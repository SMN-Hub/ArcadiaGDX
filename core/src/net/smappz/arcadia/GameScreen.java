package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.SnapshotArray;

import net.smappz.arcadia.actors.AirEnemy;
import net.smappz.arcadia.actors.AirFighter;
import net.smappz.arcadia.actors.AirSquadron;
import net.smappz.arcadia.actors.Army;
import net.smappz.arcadia.actors.Fireworks;
import net.smappz.arcadia.actors.Shoot;
import net.smappz.arcadia.levels.Level;

class GameScreen extends AbstractScreen implements GameListener {

    private AirFighter fighter;
    private Army army;
    private Fireworks fireworks;
    private Level level;

    @Override
    public void show() {
        // Stage and actors
        super.show();
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
    protected void act(float delta) {
        level.act(delta);
        stage.act(delta);
        handleCollisions();
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
    public void friendlyShoot(int shootId, Vector2 origin, float orientation) {
        fireworks.shoot(shootId, origin, orientation);
    }

    void setLevel(Level level) {
        this.level = level;
    }
}
