package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import static net.smappz.arcadia.GameScreen.HEIGHT;
import static net.smappz.arcadia.GameScreen.WIDTH;

public class LinearDriver extends Driver {
    private final Vector2 origin;
    private final double orientation;

    public LinearDriver(net.smappz.arcadia.actors.SpriteActor actor, Vector2 origin, float orientation) {
        super(actor);
        this.origin = origin;
        this.orientation = Math.toRadians(orientation);
    }

    @Override
    public void start() {
        actor.setPosition(origin.x, origin.y);
    }

    @Override
    public void act(float delta, float speed) {
        float maxDistance = speed * delta;
        Vector2 pos = actor.getPosition();
        pos.add((float)(maxDistance * Math.cos(orientation)), (float)(maxDistance * Math.sin(orientation)));
        actor.setPosition(pos);
        if (pos.x < -actor.getWidth() || pos.x > WIDTH+actor.getWidth() || pos.y < -actor.getHeight() || pos.y > HEIGHT+actor.getHeight())
            actor.setVisible(false);
    }
}
