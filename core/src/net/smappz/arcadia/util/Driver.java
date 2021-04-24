package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.actors.SpriteActor;

public abstract class Driver {
    final SpriteActor actor;

    Driver(SpriteActor actor) {
        this.actor = actor;
    }

    public abstract void start();
    public abstract void act(float delta, float speed);
    public boolean isOver() {
        return false;
    }
    public float getDirection() {
        return 0;
    }
    public static float computeAngle(Vector2 origin, Vector2 target) {
        float deltaX = target.x - origin.x;
        float deltaY = target.y - origin.y;
        float angle;
        if (FloatUtil.isZero(deltaX)) {
            angle = (target.y > origin.y) ? 90 : 270;
        } else if (FloatUtil.isZero(deltaY)) {
            angle = (target.x > origin.x) ? 0 : 180;
        } else {
            angle = (float) Math.toDegrees(Math.atan(deltaY / deltaX));
            if (deltaX < 0)
                angle += 180;
        }
        return angle;
    }
}
