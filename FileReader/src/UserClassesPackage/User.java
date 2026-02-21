package UserClassesPackage;

public class User {
    //private variables so they can't be changed after read in
    private String userID, name, email, userType;

    //creates the user
    public User(String userID, String name, String email, String userType) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.userType = userType;
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
    public String getUserType() {
        return userType;
    }

}
