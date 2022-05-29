package entities;

public class User {

    private static int id;
    private String username;
    private static String forename;
    private static String surname;
    private static String email;
    private String password;
    private int targetHours;
    private boolean isAdmin;

    public User(){}
    public User(int id, String forename, String surname, String email, int targetHours, boolean isAdmin) {
        this.id= id;
        this.forename=forename;
        this.surname= surname;
        this.email= email;
        this.targetHours=targetHours;
        this.isAdmin = isAdmin;
    }

    public static int getUserID() {
        return id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public static String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(int targetHours) {
        this.targetHours = targetHours;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }
}
