package com.example.dana.pulsetrackandroid;

import java.util.Date;

/**
 * Created by dana on 11/8/16.
 */

public class PulseLog {
    private Integer pulse;
    private String feeling;

    private Date time;

    public PulseLog() {
        pulse = 0;
        feeling = "";
        time = new Date();
    }

    public PulseLog(Integer pulse, String feeling) {
        this.pulse = pulse;
        this.feeling = feeling;
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
        return "pulse:  " + pulse + ", feeling:  " + feeling;
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
