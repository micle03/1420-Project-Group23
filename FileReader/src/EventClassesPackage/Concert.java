package EventClassesPackage;

public class Concert extends Event {
    //private variable for age
    private int ageRestriction;

    public Concert(String eventID, String title, String dateTime, String location, int capacity, String status, int ageRestriction) {
        //inherits parent class attributes
        super(eventID, title, dateTime, location, capacity, status);

        //concert specific variable
        this.ageRestriction = ageRestriction;
    }

    //getter
    public int getAgeRestriction() {
        return ageRestriction;
    }
}
