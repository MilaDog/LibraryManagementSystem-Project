/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import librarymanagementsystemproject.Books;
import librarymanagementsystemproject.BooksTakenOut;
import librarymanagementsystemproject.BooksTakenOutBookDetails;
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class TakenOutController implements Initializable {

    private String currentUser = "";
    
    private Library lib = new Library();
    
    private Books fetchedBook = null;
    private ArrayList<BooksTakenOutBookDetails> takenOutBooksDetails = new ArrayList<>();
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOutput;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private TableColumn<BooksTakenOutBookDetails, String> colBookID;
    @FXML
    private TableColumn<BooksTakenOutBookDetails, String> colBookTitle;
    @FXML
    private TableColumn<BooksTakenOutBookDetails, String> colBookAuthors;
    @FXML
    private Button btnBooksSearch;
    @FXML
    private TextField txfBookSearchInput;
    @FXML
    private TableColumn<BooksTakenOutBookDetails, String> colTakeoutID;
    @FXML
    private TableView<BooksTakenOutBookDetails> tblTakenOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){     
                tblTakenOut.getItems().clear();
                ArrayList<BooksTakenOut> takenOutBooks = lib.fetchTakenOutBooks(currentUser);
                defaultDisplay(takenOutBooks);                             
            }
        });        
    }
    
    // Method to set the currentUser so that it can be used for user related searches
    public void currentUser(String userid){
        currentUser = userid;
    }       
    
    // Shows all available books by default
    private void defaultDisplay(ArrayList<BooksTakenOut> fetchedTakeOuts){
        
        // If nothing was found, say so. Else, display what was found
        if(fetchedTakeOuts.isEmpty()){  
            tblTakenOut.setPlaceholder(new Label("You have not taken a book out yet."));
        }else{        
            
            // Clearing so no duplicated objects are in the arraylist, and to update the arraylist. (if during the time, the user had a book issued or returned as book)
            takenOutBooksDetails.clear();
            
            for(BooksTakenOut takeout: fetchedTakeOuts){
                fetchedBook = lib.fetchBookByID(takeout.getBookID());

                // Book Takeout details
                String takeoutID = takeout.getTakeoutID();
                String userid = takeout.getUserID();
                String dateTakeOut = takeout.getDateTakeout();
                String dateReturn = takeout.getDateReturn();
                boolean hasReturned = takeout.isReturned();
                
                // Book details
                String bookID = fetchedBook.getBookid();
                String title = fetchedBook.getTitle();
                String authors = fetchedBook.getAuthors();
                String genres = fetchedBook.getGenres();
                String isbn10 = fetchedBook.getIsbn10();
                String isbn13 = fetchedBook.getIsbn13();
                int amount = fetchedBook.getAmount();

                takenOutBooksDetails.add(new BooksTakenOutBookDetails(takeoutID, userid, dateTakeOut, dateReturn, hasReturned, bookID, title, authors, genres, isbn10, isbn13, amount));     
            }
            ObservableList<BooksTakenOutBookDetails> takeOuts = FXCollections.observableArrayList(takenOutBooksDetails);            
            
            colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutid"));
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));

            tblTakenOut.setItems(takeOuts);
        }// END if-else - any found books
    }

    // Method used to show all books that the user searched for according to their search input
    private void showSearchResults(ArrayList<BooksTakenOutBookDetails> books){
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutid"));

        ObservableList<BooksTakenOutBookDetails> searchedTakeOuts = FXCollections.observableArrayList(books);
        tblTakenOut.setItems(searchedTakeOuts);
    }
    
    @FXML
    private void btnBooksSearchClicked(MouseEvent event) {   
        
        tblTakenOut.getItems().clear();
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<BooksTakenOut> takenOutBooksUser = lib.fetchTakenOutBooks(currentUser);
            defaultDisplay(takenOutBooksUser);
        }else{  

            ArrayList<BooksTakenOutBookDetails> foundBooks = new ArrayList<>();
            foundBooks.clear();
            
            for(BooksTakenOutBookDetails book: takenOutBooksDetails){
                
                if(book.getTakeoutid().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    foundBooks.add(book);
                }else if(book.getBookid().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    foundBooks.add(book);
                }else if(book.getTitle().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    foundBooks.add(book);
                }else if(book.getAuthors().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    foundBooks.add(book);
                }else{
                    tblTakenOut.setPlaceholder(new Label("Found no book by that name"));                    
                }
            }  
            
            showSearchResults(foundBooks);
            
        }// END if-else - anything to search   
    }
    

    
}
