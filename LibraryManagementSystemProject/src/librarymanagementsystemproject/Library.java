/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystemproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author MilaDog
 */
public class Library {
    
    private static ResultSet fetched = null;
    private Postgres db = new Postgres();
    
    private ArrayList<Books> books = new ArrayList<>();
    private ArrayList<BooksFix> booksFix = new ArrayList<>();
    private ArrayList<BooksRequest> booksRequest = new ArrayList<>();
    private ArrayList<BooksTakenOut> booksTakenOut = new ArrayList<>();
    private ArrayList<RegisteredUsers> registeredUsers = new ArrayList<>();
    private ArrayList<Staff> staff = new ArrayList<>();
    
    
    public ArrayList<Books> fetchBooks(){
        String query = "SELECT * FROM books";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        books.clear();
        
        try{
            if(fetched.isBeforeFirst()){ // Checking if anything was returned
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    String genres = fetched.getString("genres");
                    String isbn10 = fetched.getString("isbn10");
                    String isbn13 = fetched.getString("isbn13");
                    int amount = fetched.getInt("amount");
                    
                    books.add(new Books(id, title, authors, genres, isbn10, isbn13, amount));
                }// END while                
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
                
        return books;
    }
    
    public ArrayList<Books> fetchBook(String searchBy){
        String query = "SELECT * FROM books WHERE LOWER(title) LIKE '%" + searchBy + "%' OR LOWER(authors) LIKE '%" + searchBy + "%' OR LOWER(isbn10) LIKE '%" + searchBy + "%' OR LOWER(isbn13) LIKE '%" + searchBy + "%'";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        books.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    String genres = fetched.getString("genres");
                    String isbn10 = fetched.getString("isbn10");
                    String isbn13 = fetched.getString("isbn13");
                    int amount = fetched.getInt("amount");
                    
                    books.add(new Books(id, title, authors, genres, isbn10, isbn13, amount));
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return books;
    }
    
    public Books fetchBookByID(String bookID){
        String query = "SELECT * FROM books WHERE LOWER(bookid) = LOWER('" + bookID + "')";
        fetched = db.fetch(query);
        
        Books fetchedBook = null;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    String genres = fetched.getString("genres");
                    String isbn10 = fetched.getString("isbn10");
                    String isbn13 = fetched.getString("isbn13");
                    int amount = fetched.getInt("amount");
                    
                    fetchedBook = new Books(id, title, authors, genres, isbn10, isbn13, amount);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return fetchedBook;
    }
    
    public ArrayList<Books> fetchAvailableBooks(){
        String query = "SELECT * FROM books WHERE amount > 0";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        books.clear();
        
        try{
            if(fetched.isBeforeFirst()){ // Checking if anything was returned
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    String genres = fetched.getString("genres");
                    String isbn10 = fetched.getString("isbn10");
                    String isbn13 = fetched.getString("isbn13");
                    int amount = fetched.getInt("amount");
                    
                    books.add(new Books(id, title, authors, genres, isbn10, isbn13, amount));
                }// END while                
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
                
        return books;
    }// END fetchAvailableBooks()
    
    
    public ArrayList<BooksFix> fetchFixBooks(){
        String query = "SELECT * FROM fix_books WHERE fixed = false";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        booksFix.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String fixID = fetched.getString("fixid");
                    String bookID = fetched.getString("bookid");
                    String staffID = fetched.getString("staffid");
                    String reason = fetched.getString("issue");
                    boolean fixed = fetched.getBoolean("fixed");
                    
                    booksFix.add(new BooksFix(fixID, bookID, staffID, reason, fixed));
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return booksFix;        
    }// END fetchFixBooks()
    
    public ArrayList<BooksTakenOut> fetchTakenOutBooks(){
        
        // Clearing ArrayList
        booksTakenOut.clear();
        
        String query = "SELECT * FROM taken_out WHERE returned = false";
        fetched = db.fetch(query);

        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String takeoutID = fetched.getString("takeoutid");
                    String bookid = fetched.getString("bookid");
                    String userid = fetched.getString("userid");
                    String dateTakeout = fetched.getString("date_takeout");
                    String dateReturn = fetched.getString("date_return");
                    boolean hasReturned = fetched.getBoolean("returned");

                    booksTakenOut.add(new BooksTakenOut(takeoutID, bookid, userid, dateTakeout, dateReturn, hasReturned));                    
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch  
        
        return booksTakenOut;
    }
    
    public ArrayList<BooksTakenOut> fetchTakenOutBooks(String userID){
        
        // Clearing ArrayList
        booksTakenOut.clear();
        
        String query = "SELECT * FROM taken_out WHERE returned = false AND userid = '" + userID + "'";
        fetched = db.fetch(query);

        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String takeoutID = fetched.getString("takeoutid");
                    String bookid = fetched.getString("bookid");
                    String userid = fetched.getString("userid");
                    String dateTakeout = fetched.getString("date_takeout");
                    String dateReturn = fetched.getString("date_return");
                    boolean hasReturned = fetched.getBoolean("returned");

                    booksTakenOut.add(new BooksTakenOut(takeoutID, bookid, userid, dateTakeout, dateReturn, hasReturned));                    
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch        
        
        return booksTakenOut;
    }// END takeOut()
    
    
    public ArrayList<RegisteredUsers> fetchTakeOutBookMembers(){
        String query = "SELECT DISTINCT r.* FROM registered_users AS r INNER JOIN taken_out AS t ON r.userID = t.userID WHERE returned = false";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        registeredUsers.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String userID = fetched.getString("userid");
                    String surname = fetched.getString("surname");
                    String firstName = fetched.getString("first_name");
                    
                    registeredUsers.add(new RegisteredUsers(userID, firstName, surname, null, null, null));
                }//END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();  
        }
        return registeredUsers;
    }
    
    
    public ArrayList<BooksTakenOut> fetchOutstandingBooks(){
        LocalDate dateNow = LocalDate.now();
        String query = "SELECT * FROM taken_out AS t WHERE t.returned = false AND t.date_return < '" + dateNow + "'";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        booksTakenOut.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String takeoutid = fetched.getString("takeoutid");
                    String bookid = fetched.getString("bookid");
                    String userid = fetched.getString("userid");
                    String takeoutDate = fetched.getString("date_takeout");
                    String returnDate = fetched.getString("date_return");
                    
                    booksTakenOut.add(new BooksTakenOut(takeoutid, bookid, userid, takeoutDate, returnDate, false));
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return booksTakenOut;
    }// END fetchedOutstandingBooks()
    
    
//    public ArrayList<BooksTakenOut> fetchReturnDates(String userID){
//        
//        // Clearing ArrayList
//        booksTakenOut.clear();
//        
//        if(userID == null){
//            String query = "SELECT TakeoutID, BookID, date_takeout, date_return FROM taken_out WHERE returned = false";
//            fetched = db.fetch(query);
//            
//            try{
//                if(fetched.isBeforeFirst()){
//                    while(fetched.next()){
//                        String takeoutID = fetched.getString("takeoutid");
//                        String bookid = fetched.getString("bookid");
//                        String takeout = fetched.getString("date_takeout");
//                        String returnDate = fetched.getString("date_return");
//
//                        booksTakenOut.add(new BooksTakenOut(takeoutID, bookid, null, takeout, returnDate, false));                   
//                    }// END while
//                }// END if
//            }catch(SQLException err){
//                err.printStackTrace();
//            }// END try-catch  
//            
//        }else{
//            String query = "SELECT TakeoutID, BookID, date_takeout, date_return FROM taken_out WHERE returned = false AND userid = '" + userID + "'";
//            fetched = db.fetch(query);
//            
//            try{
//                if(fetched.isBeforeFirst()){
//                    while(fetched.next()){
//                        String takeoutID = fetched.getString("takeoutid");
//                        String bookid = fetched.getString("bookid");
//                        String takeout = fetched.getString("date_taken_out");
//                        String returnDate = fetched.getString("date_return");
//
//                        booksTakenOut.add(new BooksTakenOut(takeoutID, bookid, userID, takeout, returnDate, false));                              
//                    }// END while
//                }// END if
//            }catch(SQLException err){
//                err.printStackTrace();
//            }// END try-catch  
//            
//        }// END if
//        return booksTakenOut;
//    }// END fetchReturnDates()
    
    
    public ArrayList<Staff> fetchStaffNotCurrent(String userid){
        String query = "SELECT * FROM staff AS s INNER JOIN registered_users AS r ON r.userid = s.userid WHERE r.userid != '" + userid + "'";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        staff.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String staffID = fetched.getString("staffid");
                    String userID = fetched.getString("userid");
                    String firstName = fetched.getString("first_name");
                    String surname = fetched.getString("surname");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    String email = fetched.getString("email");
                    
                    staff.add(new Staff(staffID, userID, firstName, surname, dob, phone, email));
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return staff; 
    }// END fetchStaff()
    
    public ArrayList<Staff> fetchAllStaff(){
        String query = "SELECT * FROM staff AS s INNER JOIN registered_users AS r ON r.userid = s.userid";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        staff.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String staffID = fetched.getString("staffid");
                    String userID = fetched.getString("userid");
                    String firstName = fetched.getString("first_name");
                    String surname = fetched.getString("surname");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    String email = fetched.getString("email");
                    
                    staff.add(new Staff(staffID, userID, firstName, surname, dob, phone, email));
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return staff;        
    }// END fetchAllStaff()    
    
    public ArrayList<RegisteredUsers> fetchMembersNotStaff(){
        String query = "SELECT * FROM registered_users AS r LEFT JOIN staff AS s ON s.userid = r.userid WHERE s.staffid IS NULL";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        registeredUsers.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String userID = fetched.getString("userid");
                    String surname = fetched.getString("surname");
                    String firstName = fetched.getString("first_name");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    String email = fetched.getString("email");
                    
                    registeredUsers.add(new RegisteredUsers(userID, firstName, surname, dob, phone, email));
                }//END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }//END try-catch
        return registeredUsers;
    }//END fetchMembersNotStaff()
    
    public ArrayList<RegisteredUsers> fetchAllMembers(){
        String query = "SELECT * FROM registered_users ORDER BY surname, first_name";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        registeredUsers.clear();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String userID = fetched.getString("userid");
                    String surname = fetched.getString("surname");
                    String firstName = fetched.getString("first_name");
                    String dob = fetched.getString("dob");
                    String phone = fetched.getString("phone");
                    String email = fetched.getString("email");
                    
                    registeredUsers.add(new RegisteredUsers(userID, firstName, surname, dob, phone, email));
                }//END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }//END try-catch
        return registeredUsers;
    }// END fetchAllMembers()  
    
    public ArrayList<BooksRequest> fetchRequestedBooks(){
        String query = "SELECT * FROM requested_books";
        fetched = db.fetch(query);
        
        // Clearing ArrayList
        booksRequest.clear();
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String requestID = fetched.getString("requestid");
                    String userid = fetched.getString("userid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    String isbn10 = fetched.getString("isbn10");
                    String isbn13 = fetched.getString("isbn13");
                    
                    booksRequest.add(new BooksRequest(requestID, userid, title, authors, isbn10, isbn13));
                }
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return booksRequest;
    }// END fetchRequestedBooks()
        
}
