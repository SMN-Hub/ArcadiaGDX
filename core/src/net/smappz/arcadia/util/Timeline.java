package net.smappz.arcadia.util;

import java.util.ArrayList;
import java.util.Collections;

public class Timeline {
    private float time = 0;
    private boolean pause = false;
    private ArrayList<TimelineEvent> remainingEvents = null;
    private final ArrayList<TimelineEvent> allEvents = new ArrayList<>();

    public void addEvent(float time, TimeEvent event) {
        allEvents.add(new TimelineEvent(time, event));
    }

    public void addEvent(float time, int countDown, TimeEvent event) {
        allEvents.add(new TimelineEvent(time, event, countDown));
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void restart() {
        Collections.sort(allEvents);
        remainingEvents = new ArrayList<>();
        remainingEvents.addAll(allEvents);
        time = 0;
    }

    public void act(float delta) {
        if (pause || remainingEvents == null) {
            return;
        }
        time += delta;
        while (!remainingEvents.isEmpty() && remainingEvents.get(0).getTime() < time) {
            TimelineEvent timelineEvent = remainingEvents.remove(0);
            if (timelineEvent.isReady())
                timelineEvent.getEvent().trigger();
        }
    }

    public boolean isOver() {
        return remainingEvents.isEmpty();
    }
}
