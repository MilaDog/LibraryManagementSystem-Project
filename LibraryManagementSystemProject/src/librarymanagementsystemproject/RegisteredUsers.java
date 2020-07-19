package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class RegisteredUsers {
    
    private String userID;
    private String firstName;
    private String surname;
    private String dob;
    private String phone;
    private String email;

    /**
     *
     * @param userID Takes in the generated UserID of the member
     * @param firstName Takes in the first name of the member
     * @param surname Takes in the surname of the member
     * @param dob Takes in the date of birth of the member
     * @param phone Takes in the phone number of the member
     * @param email Takes in the email of the member
     */
    public RegisteredUsers(String userID, String firstName, String surname, String dob, String phone, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
    }

    /**
     *
     * @return Member UserID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @return Member first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return Member surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @return Member Date of Birth
     */
    public String getDob() {
        return dob;
    }

    /**
     *
     * @return Member phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @return Member email address
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return String of all the member's details
     */
    @Override
    public String toString() {
        return "RegisteredUsers{" + "userID=" + userID + ", firstName=" + firstName + ", surname=" + surname + ", dob=" + dob + ", phone=" + phone + ", email=" + email + '}';
    }
    
    

}// END RegisteredUsers class
