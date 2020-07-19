package librarymanagementsystemproject;

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

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class OpenLibraryBooks {

    // Initializing necessary ArrayLists
    private ArrayList<OpenLibrary> ol = new ArrayList<>();
    private ArrayList<OpenLibrarySearch> ols = new ArrayList<>();
    
    // Initializing ArrayList of set genres
    private static ArrayList<String> genresAllowed = new ArrayList<String>(Arrays.asList("utopian fiction", "social science fiction", "tragedy", "fantasy", "fantasy fiction", "dystopian fiction", "fiction", "crime", "romance", "mystery", "political fiction", "adventure fiction", "novel", "children literature", "thriller", "science fiction", "non-fiction", "historical fiction", "horror", "biography", "poetry"));
    
    /**
     *
     * @param isbn Takes in the ISBN number of the book to search for
     * @return All of the book's details fetched by the API
     */
    public ArrayList<OpenLibrary> searchISBN(String isbn){
        // Clearing array if the user decides to search for another book, as to not have the previous search in it
        ol.clear();
        
        isbn = Utils.removeHyphens(isbn);
        
        HttpResponse<JsonNode> response = null;        
                
        getDetails:
            try{
                response = Unirest.get("https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&jscmd=data&format=json").routeParam("isbn", isbn).asJson();
            }catch(UnirestException err){
                break getDetails;
            }finally{  
                try{  
                    JsonObject jo = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();

                    if(jo.isJsonNull()){
                        break getDetails;
                    }else{
                        JsonObject main = jo.getAsJsonObject("ISBN:" + isbn);
                        JsonObject ident = main.getAsJsonObject("identifiers");  

                        String title = "";
                        String authors = "";
                        String genres = "";
                        String isbn10 = "";
                        String isbn13 = "";

                        // Checking if book has a title
                        try{
                            title = main.get("title").getAsString();
                        }catch(NullPointerException err){
                            title = "";
                        }// END try-catch


                        // Checking if the book has authors
                        try{
                            // Getting all Book Authors
                            JsonArray authorsArr = main.getAsJsonArray("authors");

                            // Looping through all authors
                            String allAuthors = "";
                            for(int i=0; i< authorsArr.size(); i++){
                                String author = authorsArr.get(i).getAsJsonObject().get("name").getAsString();
                                allAuthors = allAuthors + author + ", ";
                            }         
                            // Tidying up - removes the added ", " from the end
                            authors = allAuthors.substring(0, allAuthors.length()-2); 
                        }catch(NullPointerException err){
                            authors = "";
                        }// END try-catch


                        // Checking if the book has subjects
                        try{
                            // Getting all Book Genres that are valid - in genresAllowed
                            JsonArray subjectArr = main.getAsJsonArray("subjects");

                            // Looping through all subjects and seeing if the match the set genres
                            String allGenres = "";
                            for(int i=0; i< subjectArr.size(); i++){
                                String genre = subjectArr.get(i).getAsJsonObject().get("name").getAsString();

                                // Checking in a valid genre
                                if(genresAllowed.contains(genre.toLowerCase())){
                                    allGenres = allGenres + Utils.toTitleCase(genre) + ", ";
                                }
                            }// END loop
                            // Tidying up
                            genres = allGenres.substring(0, allGenres.length()-2);
                        }catch(NullPointerException err){
                            genres = "";
                        }// END try-catch


                        // Checking if the book has an ISBN10 - takes first ISBN10
                        try{
                            isbn10 = ident.getAsJsonArray("isbn_10").get(0).getAsString();
                        }catch(NullPointerException err){
                            isbn10 = "";
                        }


                        // Checking if the book has an ISBN13 - takes first ISBN13
                        try{
                            isbn13 = ident.getAsJsonArray("isbn_13").get(0).getAsString();
                        }catch(NullPointerException err){
                            isbn13 = "";
                        }

                        ol.add(new OpenLibrary(title, authors, genres, isbn10, isbn13));

                    }// END if - JsonNull - jo

                }catch(Exception err){
                    break getDetails;
                }// END try-catch - inner
            }// END try-catch - outer
        
        return ol;
    }// END searchISBN()
    
    /**
     *
     * @param bookTitle Takes in the title of the book to search for
     * @return All of the book's details fetched by the API
     */
    public ArrayList<OpenLibrary> searchTitle(String bookTitle){
        // Clearing array if the user decides to search for another book, as to not have the previous search in it
        ol.clear();
        
        HttpResponse<JsonNode> response = null;
        
        getDetails:
            try{
                response = Unirest.get("http://openlibrary.org/search.json?title={title}").routeParam("title", bookTitle).asJson();
            }catch(UnirestException err){
                err.printStackTrace();
            }finally{
                try{  
                    JsonObject jo = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();

                    if(jo.isJsonNull()){
                        break getDetails;
                    }else{

                        JsonArray docs = jo.getAsJsonArray("docs");

                        String title = "";
                        String authors = "";
                        String isbn = "";

                        for(JsonElement doc: docs){
                            JsonObject main = doc.getAsJsonObject();                    

                            // Checking if book has a title
                            try{
                                title = main.get("title").getAsString();
                            }catch(NullPointerException err){
                                title = "";
                            }// END try-catch


                            // Checking if the book has authors
                            try{
                                // Getting all Book Authors
                                JsonArray authorsArr = main.getAsJsonArray("author_name");

                                // Looping through all authors
                                String allAuthors = "";
                                for(JsonElement author: authorsArr){
                                    String auth = author.getAsString();
                                    allAuthors = allAuthors + auth + ", ";
                                }         
                                // Tidying up - removes the added ", " from the end
                                authors = allAuthors.substring(0, allAuthors.length()-2); 
                            }catch(NullPointerException err){
                                authors = "";
                            }// END try-catch  

                            
                            // Checking if ISBNs are supplied. If so, taking the first one. Used for both 10 and 13
                            try{
                                // Getting all ISBNs
                                JsonArray isbnArr= main.getAsJsonArray("isbn");
                                isbn = isbnArr.get(0).getAsString();
                            }catch(NullPointerException err){
                                isbn = "";
                            }// END try-catch
                            
                            
                            ol.add(new OpenLibrary(title, authors, "", isbn, isbn));  
                        }// END loop - JsonArray
                    }// END if - JsonNull - jo

                }catch(Exception err){
                    err.printStackTrace();
                    break getDetails;
                }// END try-catch - inner
            }// END try-catch - outer
        
        return ol;
    }// END searchTitle()
    
    /**
     *
     * @param bookAuthor Takes in the author of the book to search
     * @return All of the book's fetched by the API
     */
    public ArrayList<OpenLibrary> searchAuthor(String bookAuthor){
        // Clearing array if the user decides to search for another book, as to not have the previous search in it
        ol.clear();
        
        HttpResponse<JsonNode> response = null;
        
        getDetails:
            try{
                response = Unirest.get("http://openlibrary.org/search.json?author={author}").routeParam("author", bookAuthor).asJson();
            }catch(UnirestException err){
                err.printStackTrace();
            }finally{
                try{  
                    JsonObject jo = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();

                    if(jo.isJsonNull()){
                        break getDetails;
                    }else{

                        JsonArray docs = jo.getAsJsonArray("docs");

                        String title = "";
                        String authors = "";
                        String isbn = "";

                        for(JsonElement doc: docs){
                            JsonObject main = doc.getAsJsonObject();                    

                            // Checking if book has a title
                            try{
                                title = main.get("title").getAsString();
                            }catch(NullPointerException err){
                                title = "";
                            }// END try-catch


                            // Checking if the book has authors
                            try{
                                // Getting all Book Authors
                                JsonArray authorsArr = main.getAsJsonArray("author_name");

                                // Looping through all authors
                                String allAuthors = "";
                                for(JsonElement author: authorsArr){
                                    String auth = author.getAsString();
                                    allAuthors = allAuthors + auth + ", ";
                                }         
                                // Tidying up - removes the added ", " from the end
                                authors = allAuthors.substring(0, allAuthors.length()-2); 
                            }catch(NullPointerException err){
                                authors = "";
                            }// END try-catch  

                            
                            // Checking if ISBNs are supplied. If so, taking the first one. Used for both 10 and 13
                            try{
                                // Getting all ISBNs
                                JsonArray isbnArr= main.getAsJsonArray("isbn");
                                isbn = isbnArr.get(0).getAsString();
                            }catch(NullPointerException err){
                                isbn = "";
                            }// END try-catch
                            
                            
                            ol.add(new OpenLibrary(title, authors, "", isbn, isbn));
                        }// END loop - JsonArray
                    }// END if - JsonNull - jo

                }catch(Exception err){
                    err.printStackTrace();
                    break getDetails;
                }// END try-catch - inner
            }// END try-catch - outer
        
        return ol;
    }// END searchAuthor()
    
}// END OpenLibraryBooks class
