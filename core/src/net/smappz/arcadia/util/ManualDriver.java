package net.smappz.arcadia.util;

import com.badlogic.gdx.scenes.scene2d.Actor;

import static net.smappz.arcadia.util.FloatUtil.isZero;

public class ManualDriver extends Driver {
    private final Point target;
    private float direction = 0;

    public ManualDriver(Actor actor, Point origin) {
        super(actor);
        target = new Point(origin);
    }

    @Override
    public void start() {
        actor.setPosition(target.getX(), target.getY());
    }

    @Override
    public void act(float delta, float speed) {
        float deltaX = target.getX() - actor.getX();
        float deltaY = target.getY() - actor.getY();

        if (isZero(deltaX) && isZero(deltaY)) {
            return; // no need to move
        }
        // update placement
        float fullDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float maxDistance = speed * delta;
        if (fullDistance > maxDistance) {
            actor.moveBy(deltaX * maxDistance / fullDistance, deltaY * maxDistance / fullDistance);
        } else {
            actor.setPosition(target.getX(), target.getY());
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
