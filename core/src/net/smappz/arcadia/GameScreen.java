package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.smappz.arcadia.actors.*;
import net.smappz.arcadia.levels.Level;

public class GameScreen extends ScreenAdapter implements GameListener {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private Stage stage;
    private AirFighter fighter;
    private Army army;
    private net.smappz.arcadia.actors.Fireworks fireworks;
    private Level level;

    @Override
    public void show() {
        // Stage and actors
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT)); //ScreenViewport());
        //stage.setDebugAll(true);
        fighter = new AirFighter(this);
        stage.addActor(fighter);
        fighter.setZIndex(100);

        fireworks = new net.smappz.arcadia.actors.Fireworks();
        stage.addActor(fireworks);
        fireworks.setZIndex(20);

        army = new Army();
        stage.addActor(army);
        fighter.setZIndex(10);

        level.create(stage, army);

        // controls
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fighter.setShooting(true);
                fighter.moveTo(x, y);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                fighter.setShooting(false);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                fighter.moveTo(x, y);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.act(delta);
        stage.act(delta);
        handleCollisions();
        stage.draw();
    }

    private void handleCollisions() {
        SnapshotArray<Actor> shoots = fireworks.getChildren();
        Actor[] shootItems = shoots.begin();

        SnapshotArray<Actor> enemies = army.getChildren();
        Actor[] enemyItems = enemies.begin();

        for (int i=0; i < shoots.size; i++) {
            Shoot shoot = (Shoot)shootItems[i];
            if (!shoot.isVisible()) continue;

            for (int j=0; j < enemies.size; j++) {
                if (enemyItems[j] instanceof AirSquadron) continue;
                AirEnemy enemy = (AirEnemy)enemyItems[j];
                if (!enemy.isVisible() || !enemy.isTouchable()) continue;

                if (Intersector.overlapConvexPolygons(enemy.getVertices(), shoot.getVertices(), null)) {
                    shoot.setVisible(false);
                    enemy.onShoot(shoot);
                }
            }
        }

        enemies.end();
        shoots.end();
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    @Override
    public void friendlyShoot(int shootId, Vector2 origin, float orientation) {
        fireworks.shoot(shootId, origin, orientation);
    }

    void setLevel(Level level) {
        this.level = level;
    }
}
