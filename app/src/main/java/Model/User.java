package Model;



public class User {
    //EMAIL is the unique ID
    String name;
    String password;
    String email;
    String phoneNumber;
    private String userType;
    boolean loggedIn;

    public User(String name, String password, Boolean loggedIn, String email, String phoneNumber, String userType) {
        this.name = name;
        this.password = password;
        this.loggedIn = loggedIn;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.setUserType(userType);
    }


    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static boolean login(String email, String password){
        return false;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
