package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class BooksTakenOut {

    private String takeoutID;
    private String bookID;
    private String userID;
    private String dateTakeout;
    private String dateReturn;
    private boolean returned;

    public BooksTakenOut(String takeoutID, String bookID, String userID, String dateTakeout, String dateReturn, boolean returned) {
        this.takeoutID = takeoutID;
        this.bookID = bookID;
        this.userID = userID;
        this.dateTakeout = dateTakeout;
        this.dateReturn = dateReturn;
        this.returned = returned;
    }

    public String getTakeoutID() {
        return takeoutID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getUserID() {
        return userID;
    }

    public String getDateTakeout() {
        return dateTakeout;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public boolean isReturned() {
        return returned;
    }

    @Override
    public String toString() {
        return "BooksTakenOut{" + "takeoutID=" + takeoutID + ", bookID=" + bookID + ", userID=" + userID + ", dateTakeout=" + dateTakeout + ", dateReturn=" + dateReturn + ", returned=" + returned + '}';
    }
    
    
    
}// END BooksTakenOut class
