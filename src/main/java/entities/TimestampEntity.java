package entities;
import java.sql.*;

public class TimestampEntity {

    private int id;
    private int userId;
    private Timestamp start;
    private Timestamp stop;

    public TimestampEntity(int id, int userId, Timestamp start, Timestamp stop) {
        this.id = id;
        this.userId = userId;
        this.start = start;
        this.stop = stop;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getStop() {
        return stop;
    }
}
