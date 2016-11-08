package com.example.dana.pulsetrackandroid;

/**
 * Created by dana on 11/8/16.
 */

public class PulseLog {
    private Integer pulse;
    private String feeling;

    public PulseLog() {
        pulse = 0;
        feeling = "";
    }

    public PulseLog(Integer pulse, String feeling) {
        this.pulse = pulse;
        this.feeling = feeling;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        return "pulse:  " + pulse + ", feeling:  " + feeling;
    }
}
