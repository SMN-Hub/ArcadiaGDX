package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.actors.SpriteActor;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.AbstractScreen.WIDTH;

public class LinearDriver extends Driver {
    private final Vector2 origin;
    private final float orientation;
    private final double alphaX;
    private final double alphaY;
    private final boolean rotate;

    public LinearDriver(SpriteActor actor, Vector2 origin, float orientation) {
        this(actor, origin, orientation, true);
    }

    public LinearDriver(SpriteActor actor, Vector2 origin, float orientation, boolean rotate) {
        super(actor);
        this.origin = origin;
        this.orientation = orientation;
        this.rotate = rotate;
        alphaX = Math.cos(Math.toRadians(orientation));
        alphaY = Math.sin(Math.toRadians(orientation));
    }

    @Override
    public void start() {
        if (rotate)
            actor.setRotation(orientation);
        actor.setPosition(origin);
    }

    @Override
    public void act(float delta, float speed) {
        float maxDistance = speed * delta;
        Vector2 pos = actor.getPosition();
        pos.add((float)(maxDistance * alphaX), (float)(maxDistance * alphaY));
        actor.setPosition(pos);
        if (pos.x < -actor.getWidth() || pos.x > WIDTH+actor.getWidth() || pos.y < -actor.getHeight() || pos.y > HEIGHT+actor.getHeight())
            actor.setVisible(false);
    }
}
