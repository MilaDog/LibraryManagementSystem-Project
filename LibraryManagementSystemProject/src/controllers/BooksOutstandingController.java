package controllers;

import java.net.URL;
import java.time.LocalDate;
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
import librarymanagementsystemproject.BooksTakenOut;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class BooksOutstandingController implements Initializable {
    
    // Initializing necessary ArrayLists
    private ArrayList<BooksTakenOut> booksTakenOut = new ArrayList<>();
    
    //Instantiating necessary Objects
    private Library lib = new Library();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageOutstandingBooks = new Stage();    
    private LocalDate currDate = LocalDate.now();

    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private Button btnTakeoutSearch;
    @FXML
    private TextField txfTakeoutSearchInput;
    @FXML
    private Label lblDateCurrent;
    @FXML
    private AnchorPane anchorPaneDisplay;
    @FXML
    private TableView<BooksTakenOut> tblViewOutstandingBooks;
    @FXML
    private TableColumn<BooksTakenOut, String> colTakeoutID;
    @FXML
    private TableColumn<BooksTakenOut, String> colTakeoutBookID;
    @FXML
    private TableColumn<BooksTakenOut, String> colTakeoutUserID;
    @FXML
    private TableColumn<BooksTakenOut, String> colTakeoutDate;
    @FXML
    private TableColumn<BooksTakenOut, String> colTakeoutReturnDate;
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
                
                // Getting the OutstandingBooks stage
                stageOutstandingBooks = (Stage) anchorPaneBackground.getScene().getWindow();   
        
                // Clearing TableView
                tblViewOutstandingBooks.getItems().clear();    
                
                // Fetching data from PostgreDB - All OutstandingBooks
                booksTakenOut = lib.fetchOutstandingBooks();
                
                // Displaying data
                displayOutstandingBooks(booksTakenOut);
                
                // Setting CurrentDate label
                lblDateCurrent.setText("Today's Date: " + currDate.toString());
                
            }
        }); 
    }    

    private void displayOutstandingBooks(ArrayList<BooksTakenOut> fetchedBooks){  
        
        // Clearing TableView
        tblViewOutstandingBooks.getItems().clear();   

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblViewOutstandingBooks.setPlaceholder(new Label("No available books"));
        }else{        
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object 
            colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutID"));
            colTakeoutBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colTakeoutUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colTakeoutDate.setCellValueFactory(new PropertyValueFactory<>("dateTakeout"));
            colTakeoutReturnDate.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<BooksTakenOut> books = FXCollections.observableArrayList(fetchedBooks);
            tblViewOutstandingBooks.setItems(books);
        }// END if-else - any found books
    }
    
    @FXML
    private void btnTakeoutSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblViewOutstandingBooks.getItems().clear();        
        
        // If search text field is empty, displays all takeouts. If not, finds a takeout similar to what is being searched.
        if(txfTakeoutSearchInput.getText().isEmpty()){
            ArrayList<BooksTakenOut> allTakenOutBooks = lib.fetchOutstandingBooks();
            displayOutstandingBooks(allTakenOutBooks);
        }else{
            // Creating temporary ArrayList to store all found book takeouts
            ArrayList<BooksTakenOut> takeoutsSearched = new ArrayList<>();
            takeoutsSearched.clear();
            
            for(BooksTakenOut book: booksTakenOut){
                if(book.getBookID().toLowerCase().contains(txfTakeoutSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }else if(book.getUserID().toLowerCase().contains(txfTakeoutSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }else if(book.getTakeoutID().toLowerCase().contains(txfTakeoutSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }// END if-else - search filter
            }// END loop
            
            // Display a label in the TableView if no results were found, else display the results
            if(takeoutsSearched.isEmpty()){
                tblViewOutstandingBooks.setPlaceholder(new Label("Did not find any outstanding books"));   
            }else{
                displayOutstandingBooks(takeoutsSearched);
            }// END if-else - checking results
            
        }// END if-else - anything to search 
    }

    
    // Below is the HelpIcon event - displays respective Help Dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.booksOutstandingHelp(stageOutstandingBooks);
    }
    
}
