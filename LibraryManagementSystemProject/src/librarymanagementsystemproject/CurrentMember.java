/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystemproject;

/**
 *
 * @author MilaDog
 */
public class CurrentMember {
    
    private String currentUserID = "";

    public CurrentMember(String id) {
        this.currentUserID = id;
    }// END

    public String getCurrentUserID() {
        return currentUserID;
    }   
    
}
