package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class BooksTakenOutBookDetails extends Books{

    private String takeoutid;
    private String userid;
    private String returnDate;
    private String takeoutDate;
    private boolean hasReturned;

    /**
     *
     * @param takeoutid Takes in the TakeoutID of the taken out book
     * @param userid Takes in the UserID of the member that has taken out the book
     * @param takeoutDate Takes in the Date in which the member took the book out
     * @param returnDate Takes in the Date of when the member has to return the taken out book
     * @param hasReturned Takes in Boolean of whether or not the member has returned the book
     * @param bookid Takes in the BookID of the taken out book
     * @param title Takes in the Title of the taken out book
     * @param authors Takes in the Authors of the taken out book
     * @param genres Takes in the Genres of the taken out book
     * @param isbn10 Takes in the ISBN10 number of the taken out book
     * @param isbn13 Takes in the ISBN13 number of the taken out book
     * @param amount Takes in the Amount of the taken out book left available
     */
    public BooksTakenOutBookDetails(String takeoutid, String userid, String takeoutDate, String returnDate, boolean hasReturned, String bookid, String title, String authors, String genres, String isbn10, String isbn13, int amount) {
        super(bookid, title, authors, genres, isbn10, isbn13, amount);
        this.takeoutid = takeoutid;
        this.userid = userid;
        this.returnDate = returnDate;
        this.takeoutDate = takeoutDate;
        this.hasReturned = hasReturned;
    }

    /**
     *
     * @return TakeoutID of book
     */
    public String getTakeoutid() {
        return takeoutid;
    }

    /**
     *
     * @return UserID of member that took out the book
     */
    public String getUserid() {
        return userid;
    }

    /**
     *
     * @return ReturnDate of the book that was taken out
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     *
     * @return TakeoutDate of the book that was taken out
     */
    public String getTakeoutDate() {
        return takeoutDate;
    }

    /**
     *
     * @return Boolean of whether the member has returned the book or not
     */
    public boolean isHasReturned() {
        return hasReturned;
    }

    /**
     * 
     * @return String of Object
     */    
    @Override
    public String toString() {
        return "BooksTakenOutBookDetails{" + "takeoutid=" + takeoutid + ", userid=" + userid + ", returnDate=" + returnDate + ", takeoutDate=" + takeoutDate + ", hasReturned=" + hasReturned + '}';
    }
    
}// END BooksTakenOutBookDetails class
