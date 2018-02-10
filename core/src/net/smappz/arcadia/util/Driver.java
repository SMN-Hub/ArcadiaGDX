package net.smappz.arcadia.util;

public class Driver {
    private final Route route;
    private Point position = new Point(0,0);
    private float lastAngle;
    private float angle;
    private float rotation;
    private int targetIndex;

    public Driver(Route route) {
        assert route.size() > 1;
        this.route = route;
    }

    public Point getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public float getRotation() {
        float r = rotation;
        rotation = 0;
        return r;
    }

    public void start() {
        position.set(route.getPoint(0));
        targetIndex = 1;
        angle = 0;
        computeRotation();
    }

    public boolean isOver() {
        return targetIndex < 0;
    }

    public void act(float delta, float speed) {
        act(delta, speed, 0f);
    }

    private void act(float delta, float speed, float distanceDone) {
        if (targetIndex < 0) {
            return; // route is over
        }
        Point target = route.getPoint(targetIndex);
        float deltaX = target.getX() - position.getX();
        float deltaY = target.getY() - position.getY();
        float targetDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float maxDistance = (speed * delta) - distanceDone;
        if (targetDistance > maxDistance) {
            position.increase(deltaX * maxDistance / targetDistance, deltaY * maxDistance / targetDistance);
        } else {
            position.set(target);
            targetIndex++;
            if (targetIndex < route.size()) {
                computeRotation();
                if (targetDistance < maxDistance) {
                    act(delta, speed, targetDistance);
                }
            } else {
                // route is over
                targetIndex = -1;
            }
        }
    }

    private void computeRotation() {
        if (targetIndex >= 0) {
            Point target = route.getPoint(targetIndex);
            lastAngle = angle;
            float deltaX = target.getX() - position.getX();
            float deltaY = target.getY() - position.getY();
            if (deltaX == 0f) {
                angle = (target.getY() > position.getY()) ? 90 : 270;
            } else if (deltaY == 0f) {
                angle = (target.getX() > position.getX()) ? 0 : 180;
            } else {
                angle = (float) Math.toDegrees(Math.atan(deltaY / deltaX));
                if (deltaX < 0)
                    angle += 180;
            }
            rotation = angle - lastAngle;
            System.out.println(String.format("angle=%f", angle));
        }
    }
}
