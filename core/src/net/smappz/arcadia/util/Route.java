package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.actors.SpriteActor;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Vector2> points = new ArrayList<>();

    public Route() {
    }

    public Route clone() {
        Route clone = new Route();
        for (Vector2 point : points) {
            clone.points.add(new Vector2(point));
        }
        return clone;
    }

    public RouteDriver createDriver(SpriteActor actor) {
        return new RouteDriver(actor, this);
    }

    public Route addPoint(float x, float y) {
        return addPoint(new Vector2(x, y));
    }

    public Route addPoint(Vector2 p) {
        points.add(p);
        return this;
    }

    public Vector2 getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

}
