package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class Books {

    // Books Books
    private String bookid;
    private String title;
    private String authors;
    private String genres;
    private String isbn10;
    private String isbn13;
    private int amount;

    public Books(String bookid, String title, String authors, String genres, String isbn10, String isbn13, int amount) {
        this.bookid = bookid;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.amount = amount;
    }

    public String getBookid() {
        return bookid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getGenres() {
        return genres;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Library{" + "bookid=" + bookid + ", title=" + title + ", authors=" + authors + ", genres=" + genres + ", isbn10=" + isbn10 + ", isbn13=" + isbn13 + ", amount=" + amount + '}';
    }
    
    
    
}// END Books class
