package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class Staff extends RegisteredUsers{
    
    private String staffID;

    public Staff(String staffID, String userID, String firstName, String surname, String dob, String phone, String email) {
        super(userID, firstName, surname, dob, phone, email);
        this.staffID = staffID;
    }

    public String getStaffID() {
        return staffID;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffID=" + staffID + '}';
    }
    
    

}// END Staff class
