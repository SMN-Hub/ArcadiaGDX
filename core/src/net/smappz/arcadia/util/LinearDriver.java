package net.smappz.arcadia.util;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class LinearDriver extends Driver {
    private final Point origin;
    private final float orientation;

    public LinearDriver(Actor actor, Point origin, float orientation) {
        super(actor);
        this.origin = origin;
        this.orientation = orientation;
    }

    @Override
    public void start() {
        actor.setPosition(origin.getX(), origin.getY());
    }

    @Override
    public void act(float delta, float speed) {
        float maxDistance = speed * delta;
        actor.moveBy((float)(maxDistance * Math.cos(orientation)), (float)(maxDistance * Math.sin(orientation)));
    }
}
