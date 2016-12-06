package com.example.dana.pulsetrackandroid;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dana on 11/8/16.
 */

public class PulseLog implements Serializable {
    private static final AtomicInteger count = new AtomicInteger(0);

    public int getId() {
        return id;
    }

    private final int id;
    private Integer pulse;
    private String feeling;

    private Date time;

    public PulseLog() {
        pulse = 0;
        feeling = "";
        time = new Date();
        id = count.incrementAndGet();
    }

    public PulseLog(Integer pulse, String feeling) {
        id = count.incrementAndGet();
        this.pulse = pulse;
        this.feeling = feeling;
        time = new Date();
    }

    PulseLog(Integer pulse, String feeling, Date date) {
        id = count.incrementAndGet();
        this.pulse = pulse;
        this.feeling = feeling;
        this.time = date;
    }

    public Integer getPulse() {
        return pulse;
    }

    public String getFeeling() {
        return feeling;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "pulse:  " + pulse + " feeling:  " + feeling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PulseLog pulseLog = (PulseLog) o;

        if (pulse != null ? !pulse.equals(pulseLog.pulse) : pulseLog.pulse != null) return false;
        return feeling != null ? feeling.equals(pulseLog.feeling) : pulseLog.feeling == null;

    }

    @Override
    public int hashCode() {
        int result = pulse != null ? pulse.hashCode() : 0;
        result = 31 * result + (feeling != null ? feeling.hashCode() : 0);
        return result;
    }
}
