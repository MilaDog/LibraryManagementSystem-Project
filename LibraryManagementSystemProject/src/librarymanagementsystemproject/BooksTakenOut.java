package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class BooksTakenOut {

    // Initializing necessary fields
    private String takeoutID;
    private String bookID;
    private String userID;
    private String dateTakeout;
    private String dateReturn;
    private boolean returned;

    /**
     *
     * @param takeoutID Takes in the TakeoutID of the taken out book
     * @param bookID Takes in the BookID of the taken out book
     * @param userID Takes in the UserID of the member that has taken out the book
     * @param dateTakeout Takes in the Date in which the member took the book out
     * @param dateReturn Takes in the Date of when the member has to return the taken out book
     * @param returned Takes in Boolean of whether or not the member has returned the book
     */
    public BooksTakenOut(String takeoutID, String bookID, String userID, String dateTakeout, String dateReturn, boolean returned) {
        this.takeoutID = takeoutID;
        this.bookID = bookID;
        this.userID = userID;
        this.dateTakeout = dateTakeout;
        this.dateReturn = dateReturn;
        this.returned = returned;
    }

    /**
     *
     * @return TakeoutID of the book taken out
     */
    public String getTakeoutID() {
        return takeoutID;
    }

    /**
     *
     * @return BookID of the book taken out
     */
    public String getBookID() {
        return bookID;
    }

    /**
     *
     * @return UserID of the member that took the book out
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @return TakeoutDate of the book
     */
    public String getDateTakeout() {
        return dateTakeout;
    }

    /**
     *
     * @return ReturnDate of the book
     */
    public String getDateReturn() {
        return dateReturn;
    }

    /**
     *
     * @return Boolean if book was returned or not
     */
    public boolean isReturned() {
        return returned;
    }

    /**
     *
     * @return String of Object
     */
    @Override
    public String toString() {
        return "BooksTakenOut{" + "takeoutID=" + takeoutID + ", bookID=" + bookID + ", userID=" + userID + ", dateTakeout=" + dateTakeout + ", dateReturn=" + dateReturn + ", returned=" + returned + '}';
    }
    
    
    
}// END BooksTakenOut class
