package net.smappz.arcadia.util;

class TimelineEvent implements Comparable<TimelineEvent> {
    private final float time;
    private final TimeEvent event;
    private int countDown;

    TimelineEvent(float time, TimeEvent event, int countDown) {
        this.time = time;
        this.event = event;
        this.countDown = countDown;
    }

    TimelineEvent(float time, TimeEvent event) {
        this(time, event, Integer.MAX_VALUE);
    }

    float getTime() {
        return time;
    }

    TimeEvent getEvent() {
        if (!isReady())
            return null;
        countDown--;
        return event;
    }

    boolean isReady() {
        return countDown > 0;
    }

    @Override
    public int compareTo(TimelineEvent timelineEvent) {
        return Float.compare(time, timelineEvent.getTime());
    }
}
