package entities;

import java.sql.Time;

public class Request {

    private int timestampId;
    private Time newTime;
    private String description;

    public Request(int timestampId, Time newTime, String description) {
        this.timestampId = timestampId;
        this.newTime = newTime;
        this.description = description;
    }
}
