package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class LibraryActions {
    
    private static ResultSet fetched = null;
    private Postgres db = new Postgres();
    private Members mem = new Members();
    private Algorithms algor = new Algorithms();

    public LocalDate getReturnDate(){
        LocalDate returnDate = LocalDate.now().plusWeeks(2);
        return returnDate;
    }// END getReturnDate()
    
    public void issueBook(String member, String book, LocalDate currDate, LocalDate returnDate){
        boolean returned = false;
        String bookID = book.substring(0, 10);        
        String memberSplit[] = member.replace(" ", "").split(",");
        RegisteredUsers user = mem.getMemberByName(memberSplit[0], memberSplit[1]);
        String dateReturn = Utils.formatDate(returnDate);
        String takeoutID = algor.generateTakeOutID(dateReturn);
        
        // Inserting into taken_out table
        String query1 = String.format("INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('%s','%s','%s','%s','%s', %b)", takeoutID, bookID, user.getUserID(), currDate, returnDate, returned);
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
    
    public void requestBook(String userid, String title, String authors, String isbn10, String isbn13){
        String requestID = algor.generateRequestID(userid);
        
        String query = String.format("INSERT INTO requested_books(RequestID, UserID, title, authors, isbn10, isbn13) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", requestID, userid, title, authors, isbn10, isbn13);
        db.update(query);
    }// END requestBook()
    
    
    public void addStaff(String member){
        String memberName[] = member.replace(" ", "").split(",");
        String firstName = memberName[1];
        String surname = memberName[0];
        
        String staffID = algor.generateStaffID(firstName, surname);
        RegisteredUsers user = mem.getMemberByName(surname, firstName);
        
        String query = String.format("INSERT INTO staff(StaffID, UserID) VALUES ('%s', '%s')", staffID, user.getUserID());
        db.update(query);        
    }// END addStaff()
    
    
    public void removeStaff(String staff){
        String staffName[] = staff.replace(" ", "").split(",");
        String firstName = staffName[1];
        String surname = staffName[0];
        
        RegisteredUsers user = mem.getMemberByName(surname, firstName);
        
        String query = String.format("DELETE FROM staff WHERE userid = '%s'", user.getUserID());
        db.update(query);  
        
    }// END removeStaff()
    
}// END LibraryActions class
