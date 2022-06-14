package entities;

import java.sql.Date;

public class UserEntity {

    private int id;
    private final String department;
    private final Date startDay;
    private final String forename;
    private final String surname;
    private final String email;
    private final String password;
    private final String salt;
    private final int targetHours;
    private final boolean isAdmin;
    private final boolean isFirstLogin;

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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean getIsFirstLogin() {
        return isFirstLogin;
    }
}
