package Classes;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int userId;
    private int timeStampsId;
    private int targetHours;
    private String department;
    private boolean isAdmin;
    //private ArrayList<String> department;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

   public void setDepartment(String department) {
       this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    public String getDepartment() {
        return department;
    }
    public String getPassword() {
        return password;
    }
}
