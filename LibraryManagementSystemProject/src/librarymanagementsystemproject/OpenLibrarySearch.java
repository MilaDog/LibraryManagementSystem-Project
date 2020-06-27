package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 22 Jun 2020

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;


public class OpenLibrarySearch extends OpenLibrary{
    
    private int firstPublish;
    
    public OpenLibrarySearch(String title, String authors, String genres, String isbn10, String isbn13, int firstPublish){
        super(title, authors, genres, isbn10, isbn13);
        this.firstPublish = firstPublish;
    }

    public int getFirstPublish() {
        return firstPublish;
    }

    @Override
    public String toString() {
        return "OpenLibrarySearch{" + "firstPublish=" + firstPublish + '}';
    }
    
}// END OpenLibrarySearch class
