package entities;

import java.sql.Date;
import java.sql.Time;

public class Timestamp {

    private int id;
    private int userId;
    private Date date;
    private Time time;
    private boolean isStart;
    private String description;

    public Timestamp(int id, Date date, Time time, boolean isStart, String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.isStart = isStart;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }
    public int getId()
    {return id;}

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public String getDescription() {
        return description;
    }
}
