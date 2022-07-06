package entities;

import java.sql.Date;

public class UserEntity {

    private int id;
    private String department;
    private Date startDay;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private String salt;
    private int targetHours;
    private int workedHours;
    private boolean isAdmin;
    private boolean isFirstLogin;

    public UserEntity(int id, String department, Date startDay, String forename, String surname, String email, String password, String salt, int targetHours, boolean isAdmin, boolean isFirstLogin) {
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
        this.isFirstLogin = isFirstLogin;
    }
    //creating a new user
    public UserEntity( String department, Date startDay, String forename, String surname, String email, String password, String salt, int targetHours, boolean isAdmin, boolean isFirstLogin) {

        this.department = department;
        this.startDay = startDay;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.targetHours = targetHours;
        this.isAdmin = isAdmin;
        this.isFirstLogin = isFirstLogin;
    }

    public UserEntity(String department, Date startDay, String forename, String surname, String email, int targetHours, boolean isAdmin)
    {
        this.department = department;
        this.startDay = startDay;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.targetHours = targetHours;
        this.isAdmin = isAdmin;

    }

    public int getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public Date getStartDay() {
        return startDay;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public int getTargetHours() {
        return targetHours;
    }
    public int getWorkedHours() {
        return workedHours;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setTargetHours(int _targetHours) {
        targetHours = _targetHours;
    }

    public void setWorkedHours(int workedHours) { this.workedHours = workedHours;
    }
}