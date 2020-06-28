package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class RegisteredUsers {
    
    private String userID;
    private String firstName;
    private String surname;
    private String dob;
    private String phone;
    private String email;

    public RegisteredUsers(String userID, String firstName, String surname, String dob, String phone, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "RegisteredUsers{" + "userID=" + userID + ", firstName=" + firstName + ", surname=" + surname + ", dob=" + dob + ", phone=" + phone + ", email=" + email + '}';
    }
    
    

}// END RegisteredUsers class
