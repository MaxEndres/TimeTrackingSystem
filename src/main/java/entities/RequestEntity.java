package entities;

import java.sql.Date;
import java.sql.Time;

public class RequestEntity {

    private int timestampId;
    private final Time newTimeStart;
    private final Time newTimeStop;
    private final String description;
    private final String status;
    private final String type;
    private int userId;

    public RequestEntity(int timestampId, Time _newTimeStart, Time _newTimeStop, String description, String status, String type) {
        assert _newTimeStart.before(_newTimeStop) : "Stopp is before Start Time!";
        this.timestampId = timestampId;
        this.newTimeStart = _newTimeStart;
        this.newTimeStop = _newTimeStop;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public RequestEntity(int timestampId, Time _newTimeStart, Time _newTimeStop, String description, String status, String type, int userId) {
        assert _newTimeStart.before(_newTimeStop) : "Stopp is before Start Time!";
        this.timestampId = timestampId;
        this.newTimeStart = _newTimeStart;
        this.newTimeStop = _newTimeStop;
        this.description = description;
        this.status = status;
        this.type = type;
        this.userId = userId;
    }

    public RequestEntity(Time _newTimeStart, Time _newTimeStop, String description, String status, String type) {
        this.newTimeStart = _newTimeStart;
        this.newTimeStop = _newTimeStop;
        this.description = description;
        this.status = status;
        this.type = type;
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

    public void setTimestampId(int timestampId) {
        this.timestampId = timestampId;
    }
}
