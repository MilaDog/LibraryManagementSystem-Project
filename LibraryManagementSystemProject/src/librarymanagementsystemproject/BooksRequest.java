package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class BooksRequest {

    private String requestID;
    private String userID;
    private String authors;
    private String title;
    private String isbn10;
    private String isbn13;

    /**
     *
     * @param requestID Takes in the RequestID of the requested book
     * @param userID Takes in the UserID of the member that requested the book
     * @param title Takes in the Title of the requested book
     * @param authors Takes in the Authors of the requested book
     * @param isbn10 Takes in the ISBN10 number of the requested book
     * @param isbn13 Takes in the ISBN13 number of the requested book
     */
    public BooksRequest(String requestID, String userID, String title, String authors, String isbn10, String isbn13) {
        this.requestID = requestID;
        this.userID = userID;
        this.title = title;
        this.authors = authors;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    /**
     *
     * @return RequestID of requested book
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     *
     * @return UserID of the member who requested the book
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @return Title of the requested book
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @return Authors of the requested book
     */
    public String getAuthors(){
        return authors;
    }
    
    /**
     *
     * @return ISBN10 of the requested book
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     *
     * @return ISBN13 of the requested book
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     *
     * @return String of Object
     */
    @Override
    public String toString() {
        return "BooksRequest{" + "requestID=" + requestID + ", userID=" + userID + ", authors=" + authors + ", title=" + title + ", isbn10=" + isbn10 + ", isbn13=" + isbn13 + '}';
    }
     
    
}// END BooksRequest class
