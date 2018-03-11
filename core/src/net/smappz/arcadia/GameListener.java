package net.smappz.arcadia;

import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.actors.AirEnemy;
import net.smappz.arcadia.actors.Shoot;

public interface GameListener {
    Shoot friendlyShoot(int shootId, Vector2 origin, float orientation);
    Shoot enemyShoot(int shootId, Vector2 origin, float orientation);
    void onEnemyDestroy(AirEnemy plane);
    void onFighterDestroy();
}
