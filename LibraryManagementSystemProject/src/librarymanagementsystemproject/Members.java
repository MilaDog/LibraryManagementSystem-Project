package librarymanagementsystemproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import librarymanagementsystemproject.RegisteredUsers;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class Members {
    
    private Postgres db = new Postgres();
    private static ResultSet fetched = null;

    /**
     *
     */
    public Members() {
    }
    
    /**
     *
     * @param email Takes in the email of the member
     * @return Member's Object
     */
    public RegisteredUsers getMemberByEmail(String email){
        String query = "SELECT * FROM registered_users WHERE email = '" + email + "'";
        fetched = db.fetch(query);
        
        RegisteredUsers user = null;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String userID = fetched.getString("userid");
                    String firstName = fetched.getString("first_name");
                    String surname = fetched.getString("surname");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    
                    user = new RegisteredUsers(userID, firstName, surname, dob, phone, email);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return user;
    }// END getMember()
    
    /**
     *
     * @param surname Takes in the surname of the member
     * @param firstName Takes in the first name of the member
     * @return Member's Object
     */
    public RegisteredUsers getMemberByName(String surname, String firstName){
        String query = String.format("SELECT * FROM registered_users WHERE surname = '%s' AND first_name = '%s'", surname, firstName);
        fetched = db.fetch(query);
        
        RegisteredUsers user = null;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String userID = fetched.getString("userid");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    String email = fetched.getString("email");
                    
                    user = new RegisteredUsers(userID, firstName, surname, dob, phone, email);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return user;
    }
    
    /**
     *
     * @param userID Takes in the UserID of the member
     * @return Member's Object
     */
    public RegisteredUsers getMemberByUserID(String userID){
        String query = String.format("SELECT * FROM registered_users WHERE userid = '%s'", userID);
        fetched = db.fetch(query);
        
        RegisteredUsers user = null;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String firstName = fetched.getString("first_name");
                    String surname = fetched.getString("surname");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    String email = fetched.getString("email");
                    
                    user = new RegisteredUsers(userID, firstName, surname, dob, phone, email);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return user;
    }
    
}
