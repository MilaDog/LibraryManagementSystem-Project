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
import javafx.stage.Stage;
import librarymanagementsystemproject.BooksFix;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FixedBooksController implements Initializable {

    private Stage stageFixedBook = null; 
    
    private ArrayList<BooksFix> booksFix = new ArrayList<>();

    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling(); 
    
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Platform.runLater(new Runnable() {
            @Override
            public void run(){            
                
                // Getting the Member stage
                stageFixedBook = (Stage) anchorPaneBackground.getScene().getWindow();
                
                tblViewFixBookRequests.getItems().clear();
                booksFix = lib.fetchFixBooks();
                displayBooksFix(booksFix);                
            }
        });
    }  

    private void displayBooksFix(ArrayList<BooksFix> fetchedBooks){   
        
        tblViewFixBookRequests.getItems().clear();        
        
        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblViewFixBookRequests.setPlaceholder(new Label("No available books"));
        }else{        
            colFixID.setCellValueFactory(new PropertyValueFactory<>("fixID"));
            colFixBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colFixBookReason.setCellValueFactory(new PropertyValueFactory<>("reason"));

            ObservableList<BooksFix> books = FXCollections.observableArrayList(fetchedBooks);
            tblViewFixBookRequests.setItems(books);            
        }// END if-else - any found books
    }

    @FXML
    private void btnFixBookSearchClicked(MouseEvent event) {   
        
        tblViewFixBookRequests.getItems().clear();        
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfFixBookSearchInput.getText().isEmpty()){
            ArrayList<BooksFix> allBooksFixes = lib.fetchFixBooks();
            displayBooksFix(allBooksFixes);
        }else{
            ArrayList<BooksFix> bookFixes = new ArrayList<>();
            bookFixes.clear();
            
            for(BooksFix book: booksFix){
                if(book.getFixID().toLowerCase().contains(txfFixBookSearchInput.getText().toLowerCase())){
                    bookFixes.add(book);
                }else if(book.getBookID().toLowerCase().contains(txfFixBookSearchInput.getText().toLowerCase())){
                    bookFixes.add(book);
                }else if(book.getReason().toLowerCase().contains(txfFixBookSearchInput.getText().toLowerCase())){
                    bookFixes.add(book);
                }
            }
            
            if(bookFixes.isEmpty()){
                tblViewFixBookRequests.setPlaceholder(new Label("Did not find any books"));   
            }else{
                displayBooksFix(bookFixes);
            }
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnMarkFixedBookClicked(MouseEvent event) { 
        
        boolean flagSelectedBook = false;        
        BooksFix selectedBook = null;
        
        if(tblViewFixBookRequests.getSelectionModel().getSelectedItem() != null){
            selectedBook = tblViewFixBookRequests.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
            
            libActions.fixedBook(selectedBook);

            // Resetting search input
            txfFixBookSearchInput.setText("");
            
            // Filtering - updating the BooksFixTable since a book was made available due to being marked as fixed
            booksFix = lib.fetchFixBooks();            
            displayBooksFix(booksFix);            
            
        }else if(!flagSelectedBook){
            errorHandler.fixedBooksError(stageFixedBook);
        }
    }
    
}
