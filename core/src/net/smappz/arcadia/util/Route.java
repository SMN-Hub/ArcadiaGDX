package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Vector2> points = new ArrayList<>();

    public Route() {
    }

    public Route(Route r) {
        for (Vector2 point : r.points) {
            points.add(new Vector2(point));
        }
    }

    public Route addPoint(float x, float y) {
        return addPoint(new Vector2(x, y));
    }

    public Route addPoint(Vector2 p) {
        points.add(p);
        return this;
    }

    Vector2 getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

}
