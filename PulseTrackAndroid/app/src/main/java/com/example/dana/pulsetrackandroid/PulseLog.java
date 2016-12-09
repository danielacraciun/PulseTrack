package com.example.dana.pulsetrackandroid;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by dana on 11/8/16.
 */
public class PulseLog extends RealmObject {

    @PrimaryKey
    private int id;
    private Integer pulse;
    private String feeling;
    private Date time;

    public PulseLog() {
    }

    public PulseLog(int id, Integer pulse, String feeling, Date time) {
        this.id = id;
        this.pulse = pulse;
        this.feeling = feeling;
        this.time = time;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
