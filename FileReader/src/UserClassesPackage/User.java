package UserClassesPackage;

public class User {
    //private variables so they can't be changed after read in
    private String userID, name, email;

    //creates the user
    public User(String userID, String name, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

    //getters, allow getting the info from the systemUsers list but not change the users already added
    public String getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

}
