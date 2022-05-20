package entities;

import java.sql.Time;

public class Request {

    private int timestampId;
    private Time newTime;

    public Request(int timestampId, Time newTime) {
        this.timestampId = timestampId;
        this.newTime = newTime;
    }

    public int getTimestampId() {
        return timestampId;
    }

    public Time getNewTime() {
        return newTime;
    }

    public void setNewTime(Time newTime) {
        this.newTime = newTime;
    }
}
