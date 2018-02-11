package net.smappz.arcadia.util;

import com.badlogic.gdx.scenes.scene2d.Actor;

abstract class Driver {
    final Actor actor;

    Driver(Actor actor) {
        this.actor = actor;
    }

    public abstract void start();
    public abstract void act(float delta, float speed);
}
