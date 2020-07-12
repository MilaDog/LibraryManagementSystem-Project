package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 21 Jun 2020

import java.util.ArrayList;
import java.util.Arrays;


public class OpenLibrary {
    
    private String isbn13;
    private String isbn10;
    private String authors;
    private String title;
    private String genres;
    
    private static ArrayList<String> genresAllowed = new ArrayList<String>(Arrays.asList("utopian fiction", "social science fiction", "tragedy", "fantasy", "fantasy fiction", "dystopian fiction", "fiction", "crime", "romance", "mystery", "political fiction", "adventure fiction", "novel", "children literature", "thriller", "science fiction", "non-fiction", "historical fiction", "horror", "biography", "poetry"));
      
    
    public OpenLibrary(String title, String authors, String subjects, String isbn10, String isbn13){        
        this.title = title;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.authors = authors;
        this.genres = subjects;
    }// END OpenLibrary()
    
    public String getIsbn13() {
        return isbn13;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "OpenLibrary{" + "isbn13=" + isbn13 + ", isbn10=" + isbn10 + ", authors=" + authors + ", title=" + title + ", genres=" + genres + '}';
    }
    
}// END OpenLibrary class
