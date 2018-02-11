package net.smappz.arcadia.util;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class RouteDriver extends Driver{
    private final Route route;
    private float lastRotation;
    private float direction;
    private int targetIndex;

    public RouteDriver(Actor actor, Route route) {
        super(actor);
        assert route.size() > 1;
        this.route = route;
    }

    public float getDirection() {
        float r = direction;
        direction = 0;
        return r;
    }

    @Override
    public void start() {
        actor.setX(route.getPoint(0).getX());
        actor.setY(route.getPoint(0).getY());
        targetIndex = 1;
        actor.setRotation(0);
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
        float deltaX = target.getX() - actor.getX();
        float deltaY = target.getY() - actor.getY();
        float targetDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float maxDistance = (speed * delta) - distanceDone;
        if (targetDistance > maxDistance) {
            actor.moveBy(deltaX * maxDistance / targetDistance, deltaY * maxDistance / targetDistance);
        } else {
            actor.setPosition(target.getX(), target.getY());
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
            lastRotation = actor.getRotation();
            float deltaX = target.getX() - actor.getX();
            float deltaY = target.getY() - actor.getY();
            float angle;
            if (deltaX == 0f) {
                angle = (target.getY() > actor.getY()) ? 90 : 270;
            } else if (deltaY == 0f) {
                angle = (target.getX() > actor.getX()) ? 0 : 180;
            } else {
                angle = (float) Math.toDegrees(Math.atan(deltaY / deltaX));
                if (deltaX < 0)
                    angle += 180;
            }
            actor.setRotation(angle);
            direction = actor.getRotation() - lastRotation;
        }
    }

    public static void backWard(Route route, float distance) {
        if (route.size() >= 2) {
            float deltaX = route.getPoint(1).getX() - route.getPoint(0).getX();
            float deltaY = route.getPoint(1).getY() - route.getPoint(0).getY();
            float targetDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            route.getPoint(0).decrease(deltaX * distance / targetDistance, deltaY * distance / targetDistance);
        } else if (route.size() == 1) {
            route.getPoint(0).decrease(distance, 0);
        }
    }
}
