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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagementsystemproject.BooksFix;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FixedBooksController implements Initializable {

    
    // Initializing necessary ArrayLists
    private ArrayList<BooksFix> booksFix = new ArrayList<>();

    // Initializing necessary objects
    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageFixedBook = new Stage(); 
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private Button btnFixBookSearch;
    @FXML
    private TextField txfFixBookSearchInput;
    @FXML
    private AnchorPane anchorPaneDisplay;
    @FXML
    private TableView<BooksFix> tblViewFixBookRequests;
    @FXML
    private TableColumn<BooksFix, String> colFixID;
    @FXML
    private TableColumn<BooksFix, String> colFixBookID;
    @FXML
    private TableColumn<BooksFix, String> colFixBookReason;
    @FXML
    private Button btnMarkFixedBook;
    @FXML
    private Label lblMarkFixedBookInfo;
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
                
                // Getting the FixedBooks stage
                stageFixedBook = (Stage) anchorPaneBackground.getScene().getWindow();
                
                // Clearing TableView
                tblViewFixBookRequests.getItems().clear();
                
                // Fetching data from PostgreDB - all FixBook requests
                booksFix = lib.fetchFixBooks();
                
                // Displaying data
                displayBooksFix(booksFix);                
            }
        });
    }  

    private void displayBooksFix(ArrayList<BooksFix> fetchedBooks){   
        
        // Clearing TableView
        tblViewFixBookRequests.getItems().clear();        
        
        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblViewFixBookRequests.setPlaceholder(new Label("No available books"));
        }else{  
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object       
            colFixID.setCellValueFactory(new PropertyValueFactory<>("fixID"));
            colFixBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colFixBookReason.setCellValueFactory(new PropertyValueFactory<>("reason"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<BooksFix> books = FXCollections.observableArrayList(fetchedBooks);
            tblViewFixBookRequests.setItems(books);            
        }// END if-else - any found books
    }

    @FXML
    private void btnFixBookSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblViewFixBookRequests.getItems().clear();        
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfFixBookSearchInput.getText().isEmpty()){
            ArrayList<BooksFix> allBooksFixes = lib.fetchFixBooks();
            displayBooksFix(allBooksFixes);
        }else{
            // Initializing temporary ArrayList to store all results
            ArrayList<BooksFix> bookFixes = new ArrayList<>();
            bookFixes.clear();
            
            for(BooksFix book: booksFix){
                if(book.getFixID().toLowerCase().contains(txfFixBookSearchInput.getText().toLowerCase())){
                    bookFixes.add(book);
                }else if(book.getBookID().toLowerCase().contains(txfFixBookSearchInput.getText().toLowerCase())){
                    bookFixes.add(book);
                }else if(book.getReason().toLowerCase().contains(txfFixBookSearchInput.getText().toLowerCase())){
                    bookFixes.add(book);
                }// END if-else - search filter
            }// END loop
             
            // Displaying a label in TableView if no results were found. Else displaying the resutls
            if(bookFixes.isEmpty()){
                tblViewFixBookRequests.setPlaceholder(new Label("Did not find any books"));   
            }else{
                displayBooksFix(bookFixes);
            }// END if-else - checking results            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnMarkFixedBookClicked(MouseEvent event) { 
        
        boolean flagSelectedBook = false;        
        BooksFix selectedBook = null;
        
        if(tblViewFixBookRequests.getSelectionModel().getSelectedItem() != null){
            // Getting selected FixRequest
            selectedBook = tblViewFixBookRequests.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
            
            // Marking FixRequest as Fixed
            libActions.fixedBook(selectedBook);

            // Resetting search input
            txfFixBookSearchInput.setText("");
            
            // Updating the BooksFixTable since a book was made available due to being marked as fixed
            booksFix = lib.fetchFixBooks();            
            displayBooksFix(booksFix);            
            
        }else if(!flagSelectedBook){
            errorHandler.fixedBooksError(stageFixedBook);
        }// END if-else - checking if a FixRequest was selected. Else displaying respective warning dialog
    }

    
    // Below is the HelpIcon event - displays respective help dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.booksFixedHelp(stageFixedBook);
    }
    
}
