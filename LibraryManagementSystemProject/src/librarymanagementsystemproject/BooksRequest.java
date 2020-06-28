package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class BooksRequest {

    private String requestID;
    private String userID;
    private String title;
    private String isbn10;
    private String isbn13;

    public BooksRequest(String requestID, String userID, String title, String isbn10, String isbn13) {
        this.requestID = requestID;
        this.userID = userID;
        this.title = title;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    @Override
    public String toString() {
        return "BooksRequest{" + "requestID=" + requestID + ", userID=" + userID + ", title=" + title + ", isbn10=" + isbn10 + ", isbn13=" + isbn13 + '}';
    }
    
     
    
}// END BooksRequest class
