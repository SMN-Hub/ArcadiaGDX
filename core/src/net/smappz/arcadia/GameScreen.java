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
import net.smappz.arcadia.actors.Bonus;
import net.smappz.arcadia.actors.BonusContainer;
import net.smappz.arcadia.actors.Fireworks;
import net.smappz.arcadia.actors.HUD;
import net.smappz.arcadia.actors.Shoot;
import net.smappz.arcadia.descriptors.LevelScore;
import net.smappz.arcadia.levels.Level;

class GameScreen extends AbstractScreen implements GameListener {
    private enum Status {
        Launching,
        Running,
        Success,
        Fail;
    }

    private int lastBonus = -1;
    private Status status;

    private AirFighter fighter;
    private Army army;
    private Fireworks friendlyWorks;
    private Fireworks enemyWorks;
    private Fireworks bonusWorks;
    private HUD hud;
    private Level level;
    private final LevelScore score;

    GameScreen() {
        score = new LevelScore();
    }

    @Override
    public void show() {
        status = Status.Launching;
        // Stage and actors
        super.show();
        //stage.setDebugAll(true);
        fighter = new AirFighter();
        stage.addActor(fighter);
        fighter.setZIndex(100);

        friendlyWorks = new Fireworks();
        stage.addActor(friendlyWorks);
        friendlyWorks.setZIndex(20);

        bonusWorks = new Fireworks();
        stage.addActor(bonusWorks);
        bonusWorks.setZIndex(19);

        enemyWorks = new Fireworks();
        stage.addActor(enemyWorks);
        enemyWorks.setZIndex(18);

        army = new Army();
        stage.addActor(army);
        army.setZIndex(10);

        hud = new HUD(score, fighter);
        stage.addActor(hud);
        hud.toFront();

        level.create(stage, army);
        level.setPause(true);

        // controls
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (status) {
                    case Launching:
                        level.setPause(false);
                        status = Status.Running;
                }
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
        handleFriendlyFireCollisions();
        handleBonusCollisions();
        handleEnemyFireCollisions();
    }

    private void handleFriendlyFireCollisions() {
        SnapshotArray<Actor> shoots = friendlyWorks.getChildren();
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

    private void handleBonusCollisions() {
        SnapshotArray<Actor> bonuses = bonusWorks.getChildren();
        Actor[] bonusItems = bonuses.begin();

        for (int i=0; i < bonuses.size; i++) {
            BonusContainer bonus = (BonusContainer)bonusItems[i];
            if (!bonus.isVisible()) continue;

            if (Intersector.overlapConvexPolygons(fighter.getVertices(), bonus.getVertices(), null)) {
                bonus.setVisible(false);
                bonus.apply(fighter);
            }
        }
        bonuses.end();
    }

    private void handleEnemyFireCollisions() {
        if (!fighter.isVisible())
            return;

        SnapshotArray<Actor> shoots = enemyWorks.getChildren();
        Actor[] shootItems = shoots.begin();

        for (int i=0; i < shoots.size; i++) {
            Shoot shoot = (Shoot)shootItems[i];
            if (!shoot.isVisible()) continue;

            if (Intersector.overlapConvexPolygons(fighter.getVertices(), shoot.getVertices(), null)) {
                shoot.setVisible(false);
                fighter.onShoot(shoot);
            }
        }
        shoots.end();

        SnapshotArray<Actor> enemies = army.getChildren();
        Actor[] enemyItems = enemies.begin();

        for (int j=0; j < enemies.size; j++) {
            if (enemyItems[j] instanceof AirSquadron) continue;
            AirEnemy enemy = (AirEnemy)enemyItems[j];
            if (!enemy.isVisible() || !enemy.isTouchable()) continue;

            if (Intersector.overlapConvexPolygons(enemy.getVertices(), fighter.getVertices(), null)) {
                fighter.onDamage(enemy.getCurrentLife());
                enemy.onDamage(1000); // instant destroy
            }
        }
        enemies.end();
    }

    @Override
    public Shoot friendlyShoot(int shootId, Vector2 origin, float orientation) {
        return friendlyWorks.shoot(shootId, origin, orientation);
    }

    @Override
    public Shoot enemyShoot(int shootId, Vector2 origin, float orientation) {
        return enemyWorks.shoot(shootId, origin, orientation);
    }

    @Override
    public Shoot enemyHomingShoot(int shootId, Vector2 origin) {
        return enemyWorks.shoot(shootId, origin, fighter.getPosition());
    }

    @Override
    public void onEnemyDestroy(AirEnemy plane) {
        if (plane.getDescriptor().hasBonus()) {
            // drop random bonus
            lastBonus++;
            if (lastBonus >= Bonus.values().length)
                lastBonus = 0;
            bonusWorks.bonus(Bonus.values()[lastBonus], plane.getPosition());
        }
        score.increaseScore();
    }

    @Override
    public void onFighterDamage(int damage) {
        score.increaseDamages(damage);
    }

    @Override
    public void onFighterDestroy() {
        status = Status.Fail;
        level.setPause(true);
        hud.gameOver();
    }

    @Override
    public void finish() {
        status = Status.Success;
        hud.finish();
    }

    void setLevel(Level level) {
        this.level = level;
    }
}
