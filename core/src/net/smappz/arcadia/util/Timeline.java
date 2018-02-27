package net.smappz.arcadia.util;

import java.util.ArrayList;
import java.util.Collections;

public class Timeline {
    private float time = 0;
    private ArrayList<TimelineEvent> remainingEvents = null;
    private ArrayList<TimelineEvent> allEvents = new ArrayList<>();

    public void addEvent(float time, TimeEvent event) {
        allEvents.add(new TimelineEvent(time, event));
    }

    public void restart() {
        Collections.sort(allEvents);
        remainingEvents = new ArrayList<>();
        remainingEvents.addAll(allEvents);
        time = 0;
    }

    public void act(float delta) {
        if (remainingEvents == null) {
            restart();
        }
        time += delta;
        while (!remainingEvents.isEmpty() && remainingEvents.get(0).getTime() < time) {
            TimelineEvent timelineEvent = remainingEvents.remove(0);
            timelineEvent.getEvent().trigger();
        }
    }
}
