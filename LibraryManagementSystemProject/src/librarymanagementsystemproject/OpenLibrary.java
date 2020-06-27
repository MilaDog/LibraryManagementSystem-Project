package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 21 Jun 2020

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;


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
        
//        // Removing all hypens from the ISBN number
//        isbn = Utils.removeHyphens(isbn);
//        
//        try{
//            HttpResponse<JsonNode> response = Unirest.get("https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&jscmd=data&format=json").routeParam("isbn", isbn).asJson();
//  
//            JsonObject je = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();
//            JsonObject ol = je.getAsJsonObject("ISBN:" + isbn);
//            JsonObject ident = ol.getAsJsonObject("identifiers");
//                       
//            // Checking if the book has an ISBN13 number
//            try{
//                // Getting ISBN13 Number
//                this.isbn13 = ident.getAsJsonArray("isbn_13").get(0).getAsString();
//            }catch(NullPointerException err){
//                this.isbn13 = null;
//            }// END try-catch
//            
//            
//            // Checking if the book has an ISBN10 number
//            try{
//                // Getting ISBN10 Number
//                this.isbn10 = ident.getAsJsonArray("isbn_10").get(0).getAsString();
//            }catch(NullPointerException err){
//                this.isbn10 = null;
//            }// END try-catch
//            
//            
//            // Checking if the book has a title
//            try{
//                // Getting Book Title
//                this.title = ol.get("title").getAsString();
//            }catch(NullPointerException err){
//                this.title = null;
//            }// END try-catch
//            
//            
//            // Checking if the book has any authors
//            try{
//                // Getting all Book Authors
//                JsonArray authorsArr = ol.getAsJsonArray("authors");
//
//                String allAuthors = "";
//                for(int i=0; i< authorsArr.size(); i++){
//                    String author = authorsArr.get(i).getAsJsonObject().get("name").getAsString();
//                    allAuthors = allAuthors + author + ", ";
//                }            
//                this.authors = allAuthors.substring(0, allAuthors.length()-2);  
//            }catch(NullPointerException err){
//                this.authors = null;
//            }// END try-catch
//            
//            
//            // Checking if the book has any subjects (genres)
//            try{
//                // Getting all Book Genres that are valid - in genresAllowed
//                JsonArray arr = ol.getAsJsonArray("subjects");
//                String allGenres = "";
//
//                for(int i=0; i< arr.size(); i++){
//                    String genre = arr.get(i).getAsJsonObject().get("name").getAsString();
//
//                    if(genresAllowed.contains(genre.toLowerCase())){
//                        allGenres = allGenres + Utils.toTitleCase(genre) + ", ";
//                    }
//                }
//                this.genres = allGenres.substring(0, allGenres.length()-2);
//            }catch(NullPointerException err){
//                this.genres = null;
//            }// END try-catch
//            
//            
//        }catch(UnirestException err){
//            err.printStackTrace();
//        }
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
