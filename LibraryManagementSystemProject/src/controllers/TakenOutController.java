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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagementsystemproject.Books;
import librarymanagementsystemproject.BooksTakenOut;
import librarymanagementsystemproject.BooksTakenOutBookDetails;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class TakenOutController implements Initializable {

    //Initializing necessary Variables
    private String currentUser = "";
    
    // Instantiating necessary objects
    private Library lib = new Library();
    private HelpHandling helpHandler = new HelpHandling();
    private Books fetchedBook = null;
    
    private Stage stageTakenOut = new Stage();
    
    // Initializing necessary ArrayLists
    private ArrayList<BooksTakenOutBookDetails> takenOutBooksDetails = new ArrayList<>();
    private ArrayList<BooksTakenOut> takenOutBooks = new ArrayList<>();
    
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
    @FXML
    private ImageView imHelp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){    
                
                // Getting the TakenOut stage
                stageTakenOut = (Stage) anchorPaneBackground.getScene().getWindow(); 
                
                // Clearing TableView
                tblTakenOut.getItems().clear();
                
                // Fetching dtaa from PostgreDB - all TakenOutBooks
                takenOutBooks = lib.fetchTakenOutBooks();
                
                // Displaying data
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
        
        // Clearing TableView
        tblTakenOut.getItems().clear();
        
        // If nothing was found, say so. Else, display what was found
        if(fetchedTakeOuts.isEmpty()){  
            tblTakenOut.setPlaceholder(new Label("There are currently no taken out books."));
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
            
            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<BooksTakenOutBookDetails> takeOuts = FXCollections.observableArrayList(takenOutBooksDetails);            
            
            colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutid"));
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));

            tblTakenOut.setItems(takeOuts);
        }// END if-else - any found books
    }

    // Method used to show all books that the user searched for according to their search input, with necessary details of book
    private void showSearchResults(ArrayList<BooksTakenOutBookDetails> books){   
        
        // Clearing TableView
        tblTakenOut.getItems().clear();
        
        // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object  
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutid"));

        // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
        ObservableList<BooksTakenOutBookDetails> searchedTakeOuts = FXCollections.observableArrayList(books);
        tblTakenOut.setItems(searchedTakeOuts);
    }
    
    @FXML
    private void btnBooksSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblTakenOut.getItems().clear();
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<BooksTakenOut> takenOutBooksUser = lib.fetchTakenOutBooks(currentUser);
            defaultDisplay(takenOutBooksUser);
        }else{  
            // Initializing a temporary ArrayList to store results
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
                }// END if-else - search filter
            }// END loop
            
            // Displaying results
            showSearchResults(foundBooks);
            
        }// END if-else - anything to search   
    }

    
    // Below is the HelpIcon event - displays respective help dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.libraryTakenOutHelp(stageTakenOut);
    }    
}
