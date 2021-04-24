package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.actors.SpriteActor;

import java.util.ArrayList;
import java.util.List;

public class RouteInvader extends Route {
    private final List<InvaderDriver> drivers = new ArrayList<>();

    public RouteInvader() {
    }

    public Route clone() {
        Route clone = new SubRoute();
        clone.addPoint(new Vector2(getPoint(0)));
        return clone;
    }

    private class InvaderDriver extends Driver {
        private final Vector2 startPoint;
        public InvaderDriver(SpriteActor actor, Vector2 startPoint) {
            super(actor);
            this.startPoint = startPoint;
        }

        @Override
        public void start() {
            actor.setPosition(startPoint);
        }

        @Override
        public void act(float delta, float speed) {

        }
    }
    private class SubRoute extends Route {

        public Driver createDriver(SpriteActor actor) {
            return new InvaderDriver(actor, getPoint(0));
        }

    }
}
