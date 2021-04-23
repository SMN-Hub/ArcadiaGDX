package net.smappz.arcadia.util;

import java.util.ArrayList;
import java.util.List;

public class FrameSet {
    private static final int NO_FRAME = -1;

    private static class Range {
        private final int m_start;
        private final int m_end;

        private Range(int m_start, int m_end) {
            this.m_start = m_start;
            this.m_end = m_end;
        }
    }
    private boolean m_cycle = false;
    private final float m_frameLength;
    private float m_totalDuration;
    private final List<Range> m_ranges = new ArrayList<>();
    private int m_currentRange = NO_FRAME;
    private int m_currentFrame = NO_FRAME;
    private int m_endFrame = NO_FRAME;

    public FrameSet(float m_frameLength) {
        this.m_frameLength = m_frameLength;
    }

    public FrameSet addRange(int frameStart, int frameEnd) {
        m_ranges.add(new Range(frameStart, frameEnd));
        reset();
        return this;
    }

    public FrameSet cycle() {
        m_cycle = true;
        return this;
    }

    public void reset() {
        m_totalDuration = 0;
        if (!m_ranges.isEmpty()) {
            m_currentRange = 0;
            Range range = m_ranges.get(m_currentRange);
            m_currentFrame = range.m_start;
            m_endFrame = range.m_end;
        }
    }

    public boolean hasNext() {
        return m_currentFrame != NO_FRAME;
    }

    public Integer next(float delta) {
        if (m_currentFrame == NO_FRAME)
            return null;
        // Update time
        m_totalDuration += delta;
        if (m_totalDuration < m_frameLength)
            return null;
        m_totalDuration -= m_frameLength;

        int next = m_currentFrame;
        // Compute next frame
        if (++m_currentFrame > m_endFrame) {
            if (++m_currentRange >= m_ranges.size()) {
                if (m_cycle) {
                    reset();
                } else {
                    m_currentFrame = NO_FRAME;
                }
            } else {
                Range range = m_ranges.get(m_currentRange);
                m_currentFrame = range.m_start;
                m_endFrame = range.m_end;
            }
        }
        return next;
    }
}
