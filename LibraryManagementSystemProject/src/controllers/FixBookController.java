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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagementsystemproject.Books;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FixBookController implements Initializable {
    
    private String currentUser = "";

    private Stage stageFixBook = null; 
    
    private ArrayList<Books> booksAvailable = new ArrayList<>();

    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling(); 

    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private Button btnBookSearch;
    @FXML
    private TextField txfBookSearchInput;
    @FXML
    private AnchorPane anchorPaneDisplay;
    @FXML
    private TableView<Books> tblViewBooks;
    @FXML
    private TableColumn<Books, String> colBookID;
    @FXML
    private TableColumn<Books, String> colBookTitle;
    @FXML
    private TableColumn<Books, String> colBookAuthors;
    @FXML
    private Button btnFixBook;
    @FXML
    private Label lblFixBookInfo;
    @FXML
    private TextArea txfFixBookReason;
    @FXML
    private Label lblFixBookReason;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Platform.runLater(new Runnable() {
            @Override
            public void run(){            
                
                // Getting the Member stage
                stageFixBook = (Stage) anchorPaneBackground.getScene().getWindow();
                
                tblViewBooks.getItems().clear();
                booksAvailable = lib.fetchAvailableBooks();                
                displayBooks(booksAvailable);                
            }
        });
    }
    
    public void currentUser(String userid){
        currentUser = userid;
    }    

    private void displayBooks(ArrayList<Books> fetchedBooks){   
        
        tblViewBooks.getItems().clear();   

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblViewBooks.setPlaceholder(new Label("No available books"));
        }else{        
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));

            ObservableList<Books> books = FXCollections.observableArrayList(fetchedBooks);
            tblViewBooks.setItems(books);
        }// END if-else - any found books
    }
    
    @FXML
    private void btnBookSearchClicked(MouseEvent event) {   
        
        tblViewBooks.getItems().clear();        
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<Books> allBooks = lib.fetchAvailableBooks();
            displayBooks(allBooks);
        }else{
            ArrayList<Books> books = new ArrayList<>();
            books.clear();
            
            for(Books book: booksAvailable){
                if(book.getBookid().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    books.add(book);
                }else if(book.getTitle().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    books.add(book);
                }else if(book.getAuthors().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    books.add(book);
                }
            }
            
            if(books.isEmpty()){
                tblViewBooks.setPlaceholder(new Label("Did not find any books"));   
            }else{
                displayBooks(books);
            }
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnFixBookClicked(MouseEvent event) { 
        
        boolean flagSelectedBook = false;
        boolean flagFixReason = false;
        
        Books selectedBook = null;
        
        if(!txfFixBookReason.getText().replace(" ", "").isEmpty()){
            flagFixReason = true;
        }
        
        if(tblViewBooks.getSelectionModel().getSelectedItem() != null){
            selectedBook = tblViewBooks.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
        }
        
        if(flagSelectedBook & flagFixReason){
            String fixReason = txfFixBookReason.getText();
            
            libActions.fixBook(selectedBook, currentUser, fixReason);

            // Resetting search input
            txfBookSearchInput.setText("");
            
            // Filtering - updating the BooksTable since a book was made unavailable due to being fixed
            booksAvailable = lib.fetchAvailableBooks();
            displayBooks(booksAvailable);
            
            // Resetting TextArea - removing typed reason
            txfFixBookReason.setText("");
            
            
        }else if(!flagSelectedBook & flagFixReason){
            errorHandler.fixBookError(stageFixBook, "book");
        }else if(flagSelectedBook & !flagFixReason){
            errorHandler.fixBookError(stageFixBook, "reason");
        }else{
            errorHandler.fixBookError(stageFixBook, "both");
        }
    }
    
}
