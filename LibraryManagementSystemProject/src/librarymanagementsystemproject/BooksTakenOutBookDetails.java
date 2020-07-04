package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class BooksTakenOutBookDetails extends Books{

    private String takeoutid;
    private String userid;
    private String returnDate;
    private String takeoutDate;
    private boolean hasReturned;

    public BooksTakenOutBookDetails(String takeoutid, String userid, String takeoutDate, String returnDate, boolean hasReturned, String bookid, String title, String authors, String genres, String isbn10, String isbn13, int amount) {
        super(bookid, title, authors, genres, isbn10, isbn13, amount);
        this.takeoutid = takeoutid;
        this.userid = userid;
        this.returnDate = returnDate;
        this.takeoutDate = takeoutDate;
        this.hasReturned = hasReturned;
    }

    public String getTakeoutid() {
        return takeoutid;
    }

    public String getUserid() {
        return userid;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getTakeoutDate() {
        return takeoutDate;
    }

    public boolean isHasReturned() {
        return hasReturned;
    }

    @Override
    public String toString() {
        return "BooksTakenOutBookDetails{" + "takeoutid=" + takeoutid + ", userid=" + userid + ", returnDate=" + returnDate + ", takeoutDate=" + takeoutDate + ", hasReturned=" + hasReturned + '}';
    }
    
}// END BooksTakenOutBookDetails class
