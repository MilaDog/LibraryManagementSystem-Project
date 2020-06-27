/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystemproject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MilaDog
 */
public class Members {
    
    private Postgres db = new Postgres();
    private static ResultSet fetched = null;

    public Members() {
    }
    
    public String getMember(String email){
        String query = "SELECT UserID FROM registered_users WHERE email = '" + email + "'";
        fetched = db.fetch(query);
        
        String userID = "";
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    userID = fetched.getString("userid");
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return userID;
    }// END getMember()
    
    public String getMember(String surname, String firstName){
        String query = String.format("SELECT r.UserID FROM registered_users AS r WHERE r.surname = '%s' AND r.first_name = '%s'", surname, firstName);
        fetched = db.fetch(query);
        
        String userID = "";
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    userID = fetched.getString("userid");
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return userID;
    }
    
}
