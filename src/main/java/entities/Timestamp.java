package entities;

import java.sql.Date;
import java.sql.Time;

public class Timestamp {

    private int id;
    private int userId;
    private Date date;
    private Time time;
    private boolean isStart;

    public Timestamp(int id, int userId, Date date, Time time, boolean isStart) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.isStart = isStart;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
