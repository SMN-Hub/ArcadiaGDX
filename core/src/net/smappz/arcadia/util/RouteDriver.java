package net.smappz.arcadia.util;

import com.badlogic.gdx.math.Vector2;

public class RouteDriver extends Driver{
    private final Route route;
    private float direction;
    private int targetIndex;

    public RouteDriver(SpriteActor actor, Route route) {
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
        actor.setPosition(route.getPoint(0));
        targetIndex = 1;
        actor.setRotation(0);
        computeRotation();
    }

    public boolean isOver() {
        return targetIndex < 0;
    }

    public void act(float delta, float speed) {
        Vector2 pos = actor.getPosition();
        act(delta, speed, pos, 0f);
    }

    private void act(float delta, float speed, Vector2 pos, float distanceDone) {
        if (targetIndex < 0) {
            return; // route is over
        }
        Vector2 target = route.getPoint(targetIndex);
        float deltaX = target.x - pos.x;
        float deltaY = target.y - pos.y;
        float targetDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float maxDistance = (speed * delta) - distanceDone;
        if (targetDistance > maxDistance) {
            pos.add(deltaX * maxDistance / targetDistance, deltaY * maxDistance / targetDistance);
            actor.setPosition(pos);
        } else {
            pos.set(target);
            targetIndex++;
            if (targetIndex < route.size()) {
                computeRotation();
                act(delta, speed, pos, targetDistance);
            } else {
                // route is over
                targetIndex = -1;
                actor.setPosition(pos);
            }
        }
    }

    private void computeRotation() {
        if (targetIndex >= 0) {
            Vector2 target = route.getPoint(targetIndex);
            float lastRotation = actor.getRotation();
            float deltaX = target.x - actor.getX();
            float deltaY = target.y - actor.getY();
            float angle;
            if (deltaX == 0f) {
                angle = (target.y > actor.getY()) ? 90 : 270;
            } else if (deltaY == 0f) {
                angle = (target.x > actor.getX()) ? 0 : 180;
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
            float deltaX = route.getPoint(1).x - route.getPoint(0).x;
            float deltaY = route.getPoint(1).y - route.getPoint(0).y;
            float targetDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            route.getPoint(0).add(-deltaX * distance / targetDistance, -deltaY * distance / targetDistance);
        } else if (route.size() == 1) {
            route.getPoint(0).add(-distance, 0);
        }
    }
}
