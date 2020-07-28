package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class Books {

    // Initializing necessary fields
    private String bookid;
    private String title;
    private String authors;
    private String genres;
    private String isbn10;
    private String isbn13;
    private int amount;

    /**
     *
     * @param bookid Takes in the BookID of the book
     * @param title Takes in the Title of the book
     * @param authors Takes in the Authors of the book
     * @param genres Takes in the Genres of the book
     * @param isbn10 Takes in the ISBN10 number of the book
     * @param isbn13 Takes in the ISBN13 number of the book
     * @param amount Takes in the Amount of that certain book that is available
     */
    public Books(String bookid, String title, String authors, String genres, String isbn10, String isbn13, int amount) {
        this.bookid = bookid;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.amount = amount;
    }

    /**
     *
     * @return BookID of the book
     */
    public String getBookid() {
        return bookid;
    }

    /**
     *
     * @return Title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return Authors of the book
     */
    public String getAuthors() {
        return authors;
    }

    /**
     *
     * @return Genres of the book
     */
    public String getGenres() {
        return genres;
    }

    /**
     *
     * @return ISBN10 of the book
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     *
     * @return ISBN13 of the book
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     *
     * @return Amount of the book available
     */
    public int getAmount() {
        return amount;
    }

    /**
     *
     * @return String of Object
     */
    @Override
    public String toString() {
        return "Library{" + "bookid=" + bookid + ", title=" + title + ", authors=" + authors + ", genres=" + genres + ", isbn10=" + isbn10 + ", isbn13=" + isbn13 + ", amount=" + amount + '}';
    }
    
    
    
}// END Books class
