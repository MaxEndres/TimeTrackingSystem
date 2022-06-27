package entities;
import java.sql.*;

public class TimestampEntity {

    private int id;
    private final int userId;
    private final Time start;
    private Time stop;
    private final Date date;

    public TimestampEntity(int id, int userId, Time start, Time stop, Date date) {
        this.id = id;
        this.userId = userId;
        this.start = start;
        this.stop = stop;
        this.date = date;
    }
    public TimestampEntity(int userId, Time start, Time stop, Date date) {
        this.userId = userId;
        this.start = start;
        this.stop = stop;
        this.date = date;
    }

    public TimestampEntity(int userId, Time start, Date date) {
        this.userId = userId;
        this.start = start;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Time getStart() {
        return start;
    }

    public Time getStop() {
        return stop;
    }

    public Date getDate() { return date; }
}
