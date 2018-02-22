package net.smappz.arcadia.util;

public class TimelineEvent implements Comparable<TimelineEvent> {
    private final float time;
    private final TimeEvent event;

    public TimelineEvent(float time, TimeEvent event) {
        this.time = time;
        this.event = event;
    }

    public float getTime() {
        return time;
    }

    public TimeEvent getEvent() {
        return event;
    }

    @Override
    public int compareTo(TimelineEvent timelineEvent) {
        return Float.compare(time, timelineEvent.getTime());
    }
}
