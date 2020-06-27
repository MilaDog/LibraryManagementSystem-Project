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
    private Members mem = new Members();
    private Algorithms algor = new Algorithms();
    
    public ArrayList fetchBooks(){
        String query = "SELECT BookID, title, authors, amount FROM books";
        fetched = db.fetch(query);
        
        ArrayList<String> availableBooks = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){ // Checking if anything was returned
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    int amount = fetched.getInt("amount");
                    String book = String.format("%s - %s - %s - %d", id, title, authors, amount);
                    availableBooks.add(book);
                }// END while                
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
                
        return availableBooks;
    }
    
    public ArrayList fetchAvailableBooks(){
        String query = "SELECT BookID, title, authors FROM books WHERE amount > 0";
        fetched = db.fetch(query);
        
        ArrayList<String> availableBooks = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){ // Checking if anything was returned
                while(fetched.next()){
                    String id = fetched.getString("bookid");
                    String title = fetched.getString("title");
                    String authors = fetched.getString("authors");
                    String book = String.format("%s - %s - %s", id, title, authors);
                    availableBooks.add(book);
                }// END while                
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
                
        return availableBooks;
    }// END fetchAvailableBooks()
    
    
    public ArrayList fetchFixBooks(){
        String query = "SELECT f.fixid, b.title FROM fix_books AS f INNER JOIN books AS b ON b.bookid = f.bookid WHERE f.fixed = false";
        fetched = db.fetch(query);
        
        ArrayList<String> fixBooks = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String fixID = fetched.getString("fixid");
                    String title = fetched.getString("title");
                    
                    String book = String.format("%s - %s", fixID, title);
                    fixBooks.add(book);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return fixBooks;        
    }// END fetchFixBooks()
    
    
    public ArrayList fetchTakenOutBooks(String userID){
        ArrayList<String> takenOutBooks = new ArrayList<>();
        if(userID == null){
            String query = "SELECT TakeOutID, BookID FROM taken_out WHERE returned = false";
            fetched = db.fetch(query);
            
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
        }else{
            String query = "SELECT TakeOutID, BookID FROM taken_out WHERE returned = false AND userid = '" + userID + "'";
            fetched = db.fetch(query);

            try{
                if(fetched.isBeforeFirst()){
                    while(fetched.next()){
                        String takeoutID = fetched.getString("takeoutid");
                        String bookid = fetched.getString("bookid");

                        String books  = String.format("%s, %s", takeoutID, bookid);
                        takenOutBooks.add(books);                    
                    }// END while
                }// END if
            }catch(SQLException err){
                err.printStackTrace();
            }// END try-catch            
        }// END if
        
        
        return takenOutBooks;
    }// END takeOut()
    
    
    public ArrayList fetchTakeOutBookMembers(){
        String query = "SELECT DISTINCT r.surname, r.first_name FROM registered_users AS r INNER JOIN taken_out AS t ON r.userID = t.userID WHERE returned = false";
        fetched = db.fetch(query);
        
        ArrayList<String> members = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String surname = fetched.getString("surname");
                    String firstName = fetched.getString("first_name");
                    
                    String member = String.format("%s, %s", surname, firstName);
                    members.add(member);
                }//END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();  
        }
        return members;
    }
    
    
    public ArrayList fetchOutstandingBooks(){
        LocalDate dateNow = LocalDate.now();
        String query = "SELECT t.takeoutid, t.bookid, t.userid, t.date_takeout, t.date_return FROM taken_out AS t WHERE t.returned = false AND t.date_return < '" + dateNow + "'";
        fetched = db.fetch(query);
        
        ArrayList<String> outstanding = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String takeoutid = fetched.getString("takeoutid");
                    String bookid = fetched.getString("bookid");
                    String userid = fetched.getString("userid");
                    String takeoutDate = fetched.getString("date_takeout");
                    String returnDate = fetched.getString("date_return");
                    
                    String book = String.format("%s - %s - %s - %s - %s", takeoutid, bookid, userid, takeoutDate, returnDate);
                    outstanding.add(book);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }
        return outstanding;
    }// END fetchedOutstandingBooks()
    
    
    public ArrayList fetchReturnDates(String id){
        ArrayList<String> dates = new ArrayList<>();
        if(id == null){
            String query = "SELECT TakeoutID, BookID, date_takeout, date_return FROM taken_out WHERE returned = false";
            fetched = db.fetch(query);
            
            try{
                if(fetched.isBeforeFirst()){
                    while(fetched.next()){
                        String takeoutID = fetched.getString("takeoutid");
                        String bookid = fetched.getString("bookid");
                        String takeout = fetched.getString("date_taken_out");
                        String returnDate = fetched.getString("date_return");

                        String books  = String.format("%s\t%s\t%s\t%s", takeoutID, bookid, takeout, returnDate);
                        dates.add(books);                    
                    }// END while
                }// END if
            }catch(SQLException err){
                err.printStackTrace();
            }// END try-catch  
            
        }else{
            String query = "SELECT TakeoutID, BookID, date_takeout, date_return FROM taken_out WHERE returned = false AND userid = '" + id + "'";
            fetched = db.fetch(query);
            
            try{
                if(fetched.isBeforeFirst()){
                    while(fetched.next()){
                        String takeoutID = fetched.getString("takeoutid");
                        String bookid = fetched.getString("bookid");
                        String takeout = fetched.getString("date_taken_out");
                        String returnDate = fetched.getString("date_return");

                        String books  = String.format("%s\t%s\t%s\t%s", takeoutID, bookid, takeout, returnDate);
                        dates.add(books);                               
                    }// END while
                }// END if
            }catch(SQLException err){
                err.printStackTrace();
            }// END try-catch  
            
        }// END if
        return dates;
    }// END fetchReturnDates()
    
    
    public ArrayList fetchStaff(){
        String query = "SELECT r.first_name, r.surname FROM staff AS s INNER JOIN registered_users AS r ON r.userid = s.userid";
        fetched = db.fetch(query);
        
        ArrayList<String> libraryStaff = new ArrayList<>();
        
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String firstName = fetched.getString("first_name");
                    String surname = fetched.getString("surname");
                    
                    String staff = String.format("%s, %s", surname, firstName);
                    libraryStaff.add(staff);
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return libraryStaff;        
    }// END fetchStaff()
    
    
    public ArrayList fetchAllMembers(){
        String query = "SELECT r.surname, r.first_name FROM registered_users AS r ORDER BY r.surname, r.first_name";
        fetched = db.fetch(query);
        
        ArrayList<String> libraryMembers = new ArrayList<>();
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String surname = fetched.getString("surname");
                    String firstName = fetched.getString("first_name");
                    
                    String member = String.format("%s, %s", surname, firstName);
                    libraryMembers.add(member);
                }//END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }//END try-catch
        return libraryMembers;
    }// END fetchAllMembers()
    
    
    public ArrayList fetchRequestedBooks(){
        String query = "SELECT * FROM requested_books";
        fetched = db.fetch(query);
        
        ArrayList<String> requests = new ArrayList<>();
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    String requestID = fetched.getString("requestid");
                    String userid = fetched.getString("userid");
                    String title = fetched.getString("title");
                    String isbn10 = fetched.getString("isbn10");
                    String isbn13 = fetched.getString("isbn13");
                    
                    String request = String.format("%s, %s, %s, %s, %s", requestID, userid, title, isbn10, isbn13);
                    requests.add(request);
                }
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return requests;
    }// END fetchRequestedBooks()
    
    
    public LocalDate getReturnDate(){
        LocalDate returnDate = LocalDate.now().plusWeeks(2);
        return returnDate;
    }// END getReturnDate()
    
    public void issueBook(String member, String book, LocalDate currDate, LocalDate returnDate){
        boolean returned = false;
        String bookID = book.substring(0, 10);        
        String memberSplit[] = member.replace(" ", "").split(",");
        String userID = mem.getMember(memberSplit[0], memberSplit[1]);
        String dateReturn = Utils.formatDate(returnDate);
        String takeoutID = algor.generateTakeOutID(dateReturn);
        
        // Inserting into taken_out table
        String query1 = String.format("INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('%s','%s','%s','%s','%s', %b)", takeoutID, bookID, userID, currDate, returnDate, returned);
        db.update(query1);        
        
        // Decreasing number of book available
        String query2 = String.format("SELECT amount FROM books WHERE bookid = '%s'", bookID);
        fetched = db.fetch(query2);
        
        int amount = 0;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    amount = fetched.getInt("amount") - 1;
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch   
        
        String query3 = String.format("UPDATE books SET amount = %d WHERE bookid = '%s'", amount, bookID);
        db.update(query3);
        
    }// END issueBook()
    
    
    public void returnBook(String book){
        boolean returned = true;
        String bookSplit[] = book.replace(" ", "").split(",");
        String takeOutID = bookSplit[0];
        String bookID = bookSplit[1];
        
        // Updating returned field for book from taken_out table
        String query1 = String.format("UPDATE taken_out SET returned = %b WHERE takeoutid = '%s'", returned, takeOutID);
        db.update(query1);
        
        // Increasing number of book available
        String query2 = String.format("SELECT amount FROM books WHERE bookid = '%s'", bookID);
        fetched = db.fetch(query2);
        
        int amount = 0;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    amount = fetched.getInt("amount") + 1;
                }// END while
            }// END if
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch   
        
        String query3 = String.format("UPDATE books SET amount = %d WHERE bookid = '%s'", amount, bookID);
        db.update(query3);         
    }
    
    public void fixBook(String book, String staffID, String reason){
        boolean fixed = false;
        String bookID = book.substring(0, 10);
        String fixID = algor.generateFixID(bookID);
        
        // Adding to table
        String query1 = String.format("INSERT INTO fix_books(fixid, bookid, staffid, issue, fixed) VALUES ('%s', '%s', '%s', '%s', %b)", fixID, bookID, staffID, reason, fixed);
        db.update(query1);
        
        // Book is being fixed, so cannot be taken out -> decrease the amount of available books
        String query2 = String.format("SELECT amount FROM books WHERE bookid = '%s'", bookID);
        fetched = db.fetch(query2);
        
        int amount = 0;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    amount = fetched.getInt("amount") - 1;
                }
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        
        // Updating amount of avaible books of that type
        String query3 = String.format("UPDATE books SET amount = %d WHERE bookid = '%s'", amount, bookID);
        db.update(query3);        
    }// END fixBook()
    
    public void fixedBook(String fixid){
        boolean fixed = true;
        String fixID = fixid.substring(0,14);
        String bookID = fixid.substring(4,14);
        
        // Updating book as Fixed
        String query1 = String.format("UPDATE fix_books SET fixed = %b WHERE fixid = '%s'", fixed, fixID);
        db.update(query1);
        
        // Book has been fixed, so can be taken out -> increase the amount of available books
        String query2 = String.format("SELECT amount FROM books WHERE bookid = '%s'", bookID);
        fetched = db.fetch(query2);
        
        int amount = 0;
        try{
            if(fetched.isBeforeFirst()){
                while(fetched.next()){
                    amount = fetched.getInt("amount") + 1;
                }
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        
        // Updating amount of avaible books of that type
        String query3 = String.format("UPDATE books SET amount = %d WHERE bookid = '%s'", amount, bookID);
        db.update(query3);         
    }// END fixedBook()
    
    public void requestBook(String userid, String title, String isbn10, String isbn13){
        String requestID = algor.generateRequestID(userid);
        
        String query = String.format("INSERT INTO requested_books(RequestID, UserID, title, isbn10, isbn13) VALUES ('%s', '%s', '%s', '%s', '%s')", requestID, userid, title, isbn10, isbn13);
        db.update(query);
    }// END requestBook()
    
    
    public void addStaff(String member){
        String memberName[] = member.replace(" ", "").split(",");
        String firstName = memberName[1];
        String surname = memberName[0];
        
        String staffID = algor.generateStaffID(firstName, surname);
        String userID = mem.getMember(surname, firstName);
        
        String query = String.format("INSERT INTO staff(StaffID, UserID) VALUES ('%s', '%s')", staffID, userID);
        db.update(query);        
    }// END addStaff()
    
    
    public void removeStaff(String staff){
        String staffName[] = staff.replace(" ", "").split(",");
        String firstName = staffName[1];
        String surname = staffName[0];
        
        String userID = mem.getMember(surname, firstName);
        
        String query = String.format("DELETE FROM staff WHERE userid = '%s'", userID);
        db.update(query);  
        
    }// END removeStaff()
        
}
