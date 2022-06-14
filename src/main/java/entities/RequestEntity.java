package entities;

import java.sql.Time;

public class RequestEntity {

    private final int timestampId;
    private final Time newTimeStart;
    private final Time newTimeStop;
    private final String description;
    private final String status;
    private final String type;
    private int userId;

    public RequestEntity(int timestampId, Time newTimeStart, Time newTimeStop, String description, String status, String type) {
        this.timestampId = timestampId;
        this.newTimeStart = newTimeStart;
        this.newTimeStop = newTimeStop;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public RequestEntity(int timestampId, Time newTimeStart, Time newTimeStop, String description, String status, String type, int userId) {
        this.timestampId = timestampId;
        this.newTimeStart = newTimeStart;
        this.newTimeStop = newTimeStop;
        this.description = description;
        this.status = status;
        this.type = type;
        this.userId = userId;
    }

    public int getTimestampId() {
        return timestampId;
    }

    public Time getNewTimeStart() {
        return newTimeStart;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public int getUserId() { return userId; }

    public Time getNewTimeStop() {
        return newTimeStop;
    }
}
