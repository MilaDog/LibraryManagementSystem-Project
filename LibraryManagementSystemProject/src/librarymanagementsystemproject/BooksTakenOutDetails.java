package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class BooksTakenOutDetails extends Books{

    // Initializing necessary fields
    private String takeoutid;

    /**
     *
     * @param takeoutID Takes in the TakeoutID of the taken out book
     * @param bookid Takes in the BookID of the taken out book
     * @param title Takes in the Title of the taken out book
     * @param authors Takes in the Authors of the taken out book
     * @param genres Takes in the Genres of the taken out book
     * @param isbn10 Takes in the ISBN10 number of the taken out book
     * @param isbn13 Takes in the ISBN13 number of the taken out book
     * @param amount Takes in the Amount of the taken out book left available
     */
    public BooksTakenOutDetails(String takeoutID, String bookid, String title, String authors, String genres, String isbn10, String isbn13, int amount) {
        super(bookid, title, authors, genres, isbn10, isbn13, amount);
        this.takeoutid = takeoutID;
    }

    /**
     *
     * @return TakeoutID of book
     */
    public String getTakeoutID() {
        return takeoutid;
    }

    /**
     * 
     * @return String of Object
     */
    @Override
    public String toString() {
        return "BooksTakenOutDetails{" + "takeoutid=" + takeoutid + '}';
    }
    
    
    
}// END BooksTakenOutDetails class
