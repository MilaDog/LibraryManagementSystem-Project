package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class OpenLibrarySearch extends OpenLibrary{
    
    private int firstPublish;
    
    /**
     *
     * Extends OpenLibrary
     * 
     * @param title Takes in the title of the book
     * @param authors Takes in the authors of the book as a String
     * @param genres Takes in the genres of the book as a String
     * @param isbn10 Takes in the ISBN10 number of the book
     * @param isbn13 Takes in the ISBN13 number of the book
     * @param firstPublish Takes in the first year the book was published in
     */
    public OpenLibrarySearch(String title, String authors, String genres, String isbn10, String isbn13, int firstPublish){
        super(title, authors, genres, isbn10, isbn13);
        this.firstPublish = firstPublish;
    }

    /**
     *
     * @return The book's first publish year
     */
    public int getFirstPublish() {
        return firstPublish;
    }

    /**
     *
     * @return String of all the book's details
     */
    @Override
    public String toString() {
        return "OpenLibrarySearch{" + "firstPublish=" + firstPublish + '}';
    }
    
}// END OpenLibrarySearch class
