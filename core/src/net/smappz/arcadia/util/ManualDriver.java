package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.actors.SpriteActor;

import static net.smappz.arcadia.util.FloatUtil.isZero;

public class ManualDriver extends Driver {
    private final Vector2 target;
    private float direction = 0;

    public ManualDriver(SpriteActor actor, Vector2 origin) {
        super(actor);
        target = new Vector2(origin);
    }

    @Override
    public void start() {
        actor.setPosition(target);
    }

    @Override
    public void act(float delta, float speed) {
        //actor.setRotation(actor.getRotation() + delta * 20);
        Vector2 pos = actor.getPosition();
        float deltaX = target.x - pos.x;
        float deltaY = target.y - pos.y;

        if (isZero(deltaX) && isZero(deltaY)) {
            return; // no need to move
        }
        // update placement
        float fullDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float maxDistance = speed * delta;
        if (fullDistance > maxDistance) {
            pos.add(deltaX * maxDistance / fullDistance, deltaY * maxDistance / fullDistance);
            actor.setPosition(pos);
        } else {
            actor.setPosition(target);
        }
        direction = deltaY;
    }

    public void setTarget(float x, float y) {
        target.set(x, y);
    }

    public float getDirection() {
        return direction;
    }
}
