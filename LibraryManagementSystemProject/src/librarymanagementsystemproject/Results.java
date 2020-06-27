/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystemproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MilaDog
 */
public class Results {
    
    private static ResultSet fetched = null;
    private Postgres db = new Postgres();
    
    public ArrayList fetchAvailableBooks(){
        String query = "SELECT BookID, title, authors, amount FROM books WHERE amount > 0";
        fetched = db.fetch(query);
        
        ArrayList<String> availableBooks = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){ // Checking if anything was returned
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    int amount = fetched.getInt("amount");
                    String book = String.format("%s\t%s\t%s\t%d", id, title, authors, amount);
                    availableBooks.add(book);
                }// END while                
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
                
        return availableBooks;
    }// END fetchAvailableBooks()
    
    
    public ArrayList takeOut(){
        String query = "SELECT TakeOutID, BookID FROM taken_out";
        fetched = db.fetch(query);
        
        ArrayList<String> takenOutBooks = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String takeoutID = fetched.getString("takeoutid");
                    String bookid = fetched.getString("bookid");
                    
                    String books  = String.format("%s\t%s", takeoutID, bookid);
                    takenOutBooks.add(books);                    
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return takenOutBooks;
    }// END takeOut()
    
}
