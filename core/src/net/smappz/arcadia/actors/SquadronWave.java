package net.smappz.arcadia.actors;

import net.smappz.arcadia.util.TimeEvent;

public abstract class SquadronWave implements TimeEvent {
    private Squadron squadron;

    protected abstract Squadron init();

    @Override
    public void trigger() {
        if (squadron == null) {
            squadron = init();
        } else {
            squadron.restart();
        }
    }
}
