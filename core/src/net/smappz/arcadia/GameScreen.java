package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.smappz.arcadia.util.Route;

public class GameScreen extends ScreenAdapter implements GameListener {
    private Stage stage;
    private Scroller scroller;
    private AirFighter fighter;
    private Army army;
    private Fireworks fireworks;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void show() {
        // Stage and actors
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT)); //ScreenViewport());
        //stage.setDebugAll(true);
        scroller = new Scroller();
        stage.addActor(scroller);
        fighter = new AirFighter(this);
        stage.addActor(fighter);
        fireworks = new Fireworks();
        stage.addActor(fireworks);
        createEnemies();

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

    private void createEnemies() {
        army = new Army();
        stage.addActor(army);
        Route r1 = new Route();
        r1.addPoint(-30, 200).addPoint(600, 200).addPoint(900, 300).addPoint(1000, 400);
        r1.addPoint(900, 500).addPoint(800, 600).addPoint(700, 650).addPoint(600, 600);
        r1.addPoint(500, 500).addPoint(400, 300).addPoint(400, -30);
        AirSquadron a = new AirSquadron(army, r1, 1, 1, 3, 4, 1, 1);
        a.setCycle(true);

        Route r2 = new Route();
        r2.addPoint(-30, 400).addPoint(1300, 400);
        AirEnemy e1 = new AirEnemy(2, r2);
        e1.setCycle(true);
        army.addActor(e1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}
