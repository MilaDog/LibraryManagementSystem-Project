package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class BooksTakenOutDetails extends Books{

    private String takeoutid;

    public BooksTakenOutDetails(String takeoutID, String bookid, String title, String authors, String genres, String isbn10, String isbn13, int amount) {
        super(bookid, title, authors, genres, isbn10, isbn13, amount);
        this.takeoutid = takeoutID;
    }

    public String getTakeoutID() {
        return takeoutid;
    }

    @Override
    public String toString() {
        return "BooksTakenOutDetails{" + "takeoutid=" + takeoutid + '}';
    }
    
    
    
}// END BooksTakenOutDetails class
