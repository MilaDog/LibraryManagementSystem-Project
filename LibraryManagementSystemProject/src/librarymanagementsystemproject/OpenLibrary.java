package librarymanagementsystemproject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class OpenLibrary {
    
    // Initializing necessary fields
    private String isbn13;
    private String isbn10;
    private String authors;
    private String title;
    private String genres;
     
    /**
     *
     * @param title Takes in the title of the book
     * @param authors Takes in the authors of the book as a String
     * @param subjects Takes in the subjects (genres) of the book as a String
     * @param isbn10 Takes in the ISBN10 number of the book
     * @param isbn13 Takes in the ISBN13 number of the book
     */
    public OpenLibrary(String title, String authors, String subjects, String isbn10, String isbn13){        
        this.title = title;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.authors = authors;
        this.genres = subjects;
    }// END OpenLibrary()
    
    /**
     *
     * @return Book ISBN13 number
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     *
     * @return Book ISBN10 number
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     *
     * @return Book authors
     */
    public String getAuthors() {
        return authors;
    }

    /**
     *
     * @return Book title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return Book genres/subjects
     */
    public String getGenres() {
        return genres;
    }

    /**
     *
     * @return String of all of the book's details
     */
    @Override
    public String toString() {
        return "OpenLibrary{" + "isbn13=" + isbn13 + ", isbn10=" + isbn10 + ", authors=" + authors + ", title=" + title + ", genres=" + genres + '}';
    }
    
}// END OpenLibrary class
