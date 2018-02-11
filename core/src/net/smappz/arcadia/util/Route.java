package net.smappz.arcadia.util;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Point> points = new ArrayList<>();

    public Route() {
    }

    public Route(Route r) {
        for (Point point : r.points) {
            points.add(new Point(point));
        }
    }

    public Route addPoint(float x, float y) {
        return addPoint(new Point(x, y));
    }

    public Route addPoint(Point p) {
        points.add(p);
        return this;
    }

    Point getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

}
