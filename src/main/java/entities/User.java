package entities;

import java.sql.Date;

public class User {

    private int id;
    private String department;
    private Date startDay;
    private static String forename;
    private static String surname;
    private static String email;
    private String password;
    private String salt;
    private int targetHours;
    private boolean isAdmin;

    public User(String department, Date startDay, String forename, String surname, String email, String password, int targetHours, boolean isAdmin) {
        this.department = department;
        this.startDay = startDay;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.targetHours = targetHours;
        this.isAdmin = isAdmin;
    }

    public User(int id, String department, Date startDay, String forename, String surname, String email, String password, String salt, int targetHours, boolean isAdmin) {
        this.id = id;
        this.department = department;
        this.startDay = startDay;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.targetHours = targetHours;
        this.isAdmin = isAdmin;
    }

    public String getDepartment() {
        return department;
    }

    public Date getStartDay() {
        return startDay;
    }

    public static String getForename() {
        return forename;
    }

    public static String getSurname() {
        return surname;
    }

    public static String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getTargetHours() {
        return targetHours;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
}
