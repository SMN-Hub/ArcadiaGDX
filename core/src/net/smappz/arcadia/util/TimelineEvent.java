package net.smappz.arcadia.util;

class TimelineEvent implements Comparable<TimelineEvent> {
    private final float time;
    private final TimeEvent event;
    private final TimeEvent eventAfter;
    private int countDown;

    TimelineEvent(float time, int countDown, TimeEvent event, TimeEvent eventAfter) {
        this.time = time;
        this.event = event;
        this.eventAfter = eventAfter;
        this.countDown = countDown;
    }

    TimelineEvent(float time, TimeEvent event) {
        this(time, -1, event, null);
    }

    float getTime() {
        return time;
    }

    TimeEvent getEvent() {
        if (countDown == 0)
            return eventAfter;
        countDown--;
        return event;
    }

    @Override
    public int compareTo(TimelineEvent timelineEvent) {
        return Float.compare(time, timelineEvent.getTime());
    }
}
