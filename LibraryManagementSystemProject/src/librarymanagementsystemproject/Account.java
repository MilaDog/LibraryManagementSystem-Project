package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 07 Jul 2020

public class Account {
    
    private Postgres db = new Postgres();

    public void updatePassword(String userid, String password){
        String query = "UPDATE login_details SET password = '"+ password + "' WHERE userid = '" + userid + "'";
        db.update(query);
    }
    
}// END Account class
