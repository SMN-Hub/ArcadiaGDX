package net.smappz.arcadia.util;

import java.util.ArrayList;
import java.util.Collections;

public class Timeline {
    private final TimeEventListener listener;
    private float time = 0;
    private final ArrayList<TimelineEvent> queue = new ArrayList<>();

    public Timeline(TimeEventListener listener) {
        this.listener = listener;
    }

    public void addEvent(float time, TimeEvent event) {
        queue.add(new TimelineEvent(time, event));
        Collections.sort(queue);
    }
    public void act(float delta) {
        time += delta;
        while (!queue.isEmpty() && queue.get(0).getTime() < time) {
            TimelineEvent timelineEvent = queue.remove(0);
            listener.onEvent(timelineEvent.getEvent());
        }
    }
}
