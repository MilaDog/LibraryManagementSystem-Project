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
import librarymanagementsystemproject.BooksRequest;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class RequestedBooksController implements Initializable {
    
    // Initializing necessary ArrayLists
    private ArrayList<BooksRequest> booksRequested = new ArrayList<>();
    
    // Instantiating necessary Objects
    private Library lib = new Library();
    private HelpHandling helpHandler = new HelpHandling();
    private Stage stageRequestedBooks = new Stage();

    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private Button btnRequestedBooksSearch;
    @FXML
    private TextField txfRequestedBooksSearchInput;
    @FXML
    private AnchorPane anchorPaneDisplay;
    @FXML
    private TableView<BooksRequest> tblViewRequestedBooks;
    @FXML
    private TableColumn<BooksRequest, String> colRequestedID;
    @FXML
    private TableColumn<BooksRequest, String> colRequestedAuthors;
    @FXML
    private TableColumn<BooksRequest, String> colRequestedBookTitle;
    @FXML
    private TableColumn<BooksRequest, String> colRequestedBookISBN10;
    @FXML
    private TableColumn<BooksRequest, String> colRequestedBookISBN13;
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
                
                // Getting the RequestedBooks stage
                stageRequestedBooks = (Stage) anchorPaneBackground.getScene().getWindow();  
        
                // Clearing TableView
                tblViewRequestedBooks.getItems().clear(); 
                
                // Fetching data from PostgreDB - all RequestedBooks
                booksRequested = lib.fetchRequestedBooks();
                
                // Displaying data
                displayRequestedBooks(booksRequested);                
            }
        }); 
    }   
    
    private void displayRequestedBooks(ArrayList<BooksRequest> fetchedBooks){   
        
        // Clearing TableView
        tblViewRequestedBooks.getItems().clear();    

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblViewRequestedBooks.setPlaceholder(new Label("No available books"));
        }else{        
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object  
            colRequestedID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
            colRequestedBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colRequestedAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            colRequestedBookISBN10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
            colRequestedBookISBN13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<BooksRequest> books = FXCollections.observableArrayList(fetchedBooks);
            tblViewRequestedBooks.setItems(books);
        }// END if-else - any found books
    }

    @FXML
    private void btnRequestedBooksSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblViewRequestedBooks.getItems().clear();        
        
        // If search text field is empty, displays all takeouts. If not, finds a takeout similar to what is being searched.
        if(txfRequestedBooksSearchInput.getText().isEmpty()){
            ArrayList<BooksRequest> allBooksRequested = lib.fetchRequestedBooks();
            displayRequestedBooks(allBooksRequested);
        }else{
            ArrayList<BooksRequest> booksRequestedSearch = new ArrayList<>();
            booksRequestedSearch.clear();
            
            for(BooksRequest book: booksRequested){
                if(book.getRequestID().toLowerCase().contains(txfRequestedBooksSearchInput.getText().toLowerCase())){
                    booksRequestedSearch.add(book);
                }else if(book.getAuthors().toLowerCase().contains(txfRequestedBooksSearchInput.getText().toLowerCase())){
                    booksRequestedSearch.add(book);
                }else if(book.getTitle().toLowerCase().contains(txfRequestedBooksSearchInput.getText().toLowerCase())){
                    booksRequestedSearch.add(book);
                }else if(book.getIsbn10().toLowerCase().contains(txfRequestedBooksSearchInput.getText().toLowerCase())){
                    booksRequestedSearch.add(book);                    
                }else if(book.getIsbn13().toLowerCase().contains(txfRequestedBooksSearchInput.getText().toLowerCase())){
                    booksRequestedSearch.add(book);
                }// END if-else - search filter
            }// END loop
            
            if(booksRequestedSearch.isEmpty()){
                tblViewRequestedBooks.setPlaceholder(new Label("Did not find any requested books"));   
            }else{
                displayRequestedBooks(booksRequestedSearch);
            }// END if-else - display search results
            
        }// END if-else - anything to search 
    }

    
    // Below is the HelpIcon event - displays respective help dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.booksRequestedHelp(stageRequestedBooks);
    }
    
}
