package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class Staff extends RegisteredUsers{
    
    private String staffID;

    /**
     *  Extends RegisteredUsers
     * 
     * @param staffID Takes in the generated StaffID of the Staff member
     * @param userID Takes in the UserID of the member
     * @param firstName Takes in the first name of the member
     * @param surname Takes in the surname of the member
     * @param dob Takes in the date of birth of the member
     * @param phone Takes in the phone number of the member
     * @param email Takes in the email of the member
     */
    public Staff(String staffID, String userID, String firstName, String surname, String dob, String phone, String email) {
        super(userID, firstName, surname, dob, phone, email);
        this.staffID = staffID;
    }

    /**
     *
     * @return Member StaffID
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     *
     * @return String of Staff member details
     */
    @Override
    public String toString() {
        return "Staff{" + "staffID=" + staffID + '}';
    }
    
    

}// END Staff class
