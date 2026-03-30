package com.group23.model;
//Staff is subclass of user... with an userid email n string also super calls the contructor so user will store the data
public class Staff extends User {
    public Staff(String userId, String name, String email) {
        super(userId, name, email);
    }
      //if system asks how many boookings return  5
    @Override
    public int getMaxConfirmedBookings() {
        return 5;
    }

    @Override
    public String getUserType() {
        return "Staff";
    }
}
//create map of user heiharcy in the morning ai