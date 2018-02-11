package net.smappz.arcadia.util;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    void increase(float x, float y) {
        this.x += x;
        this.y += y;
    }

    void decrease(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void set(Point target) {
        x = target.x;
        y = target.y;
    }
}
