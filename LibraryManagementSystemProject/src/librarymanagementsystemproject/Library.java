package librarymanagementsystemproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class Library {
    
    // Initializing necessary Objects
    private static ResultSet fetched = null;
    private Postgres db = new Postgres();
    
    
    // Initializing necessary ArrayLists
    private ArrayList<Books> books = new ArrayList<>();
    private ArrayList<BooksFix> booksFix = new ArrayList<>();
    private ArrayList<BooksRequest> booksRequest = new ArrayList<>();
    private ArrayList<BooksTakenOut> booksTakenOut = new ArrayList<>();
    private ArrayList<RegisteredUsers> registeredUsers = new ArrayList<>();
    private ArrayList<Staff> staff = new ArrayList<>();
    
    /**
     *
     * @return ArrayList, of all books, of type Books
     */
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
    
    /**
     *
     * @param searchBy Takes in the title/authors/isbn10/isbn13 of the book to search
     * @return ArrayList, of found books, of type Books
     */
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
    
    /**
     *
     * @param bookID BookID to search for
     * @return Books Object of found book
     */
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
    
    /**
     *
     * @return ArrayList, of all available books in the library, of type Books
     */
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
    
    /**
     *
     * @return ArrayList, of all book fix requests, of type BooksFix
     */
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
    
    /**
     *
     * @return ArrayList, of all taken out books, of type BooksTakenOut
     */
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
    
    /**
     *
     * @param userID Takes in member UserID to use t find any taken out books by the member
     * @return ArrayList, of all taken out books by the member, of type BooksTakenOut
     */
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
    
    /**
     *
     * @return ArrayList, of all members that have taken out a book, of type RegisteredUsers
     */
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
    
    /**
     *
     * @return ArrayList, of all outstanding books, of type BooksTakenOut
     */
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

    /**
     *
     * @param userid Takes in the member UserID to be used to filter out of all Staff members 
     * @return ArrayList, of all staff members except the current staff member, of type Staff
     */
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
    
    /**
     *
     * @return ArrayList, of all staff members, of type Staff
     */
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
    
    /**
     *
     * @return ArrayList, of all non-staff members, of type RegisteredUsers
     */
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
    
    /**
     *
     * @return ArrayList, of all registered users, of type RegisteredUsers
     */
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
    
    /**
     *
     * @return ArrayList, of all requested books, of type BooksRequest
     */
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
