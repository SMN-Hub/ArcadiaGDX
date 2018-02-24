package net.smappz.arcadia.actors;

import net.smappz.arcadia.util.TimeEvent;

public abstract class SquadronWave implements TimeEvent {
    private AirSquadron squadron;

    protected abstract AirSquadron init();

    @Override
    public void trigger() {
        if (squadron == null) {
            squadron = init();
        } else {
            squadron.restart();
        }
    }
}
