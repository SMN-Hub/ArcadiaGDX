package net.smappz.arcadia.util;

abstract class Driver {
    final SpriteActor actor;

    Driver(SpriteActor actor) {
        this.actor = actor;
    }

    public abstract void start();
    public abstract void act(float delta, float speed);
}
