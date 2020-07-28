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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.OpenLibrary;
import librarymanagementsystemproject.OpenLibraryBooks;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class RequestBookController implements Initializable {
    
    // Initializing necessary variables
    private String currentUser = "";
    
    // Instantiating necessary Objects
    private LibraryActions libActions = new LibraryActions();
    private OpenLibraryBooks olBooks = new OpenLibraryBooks();
    private ErrorHandling errorHandler = new ErrorHandling();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageBookRequest = null;

    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOutput;
    @FXML
    private TableView<OpenLibrary> tblRequestBook;
    @FXML
    private TableColumn<OpenLibrary, String> colBookTitle;
    @FXML
    private TableColumn<OpenLibrary, String> colBookAuthors;
    @FXML
    private TableColumn<OpenLibrary, String> colBookGenres;
    @FXML
    private TableColumn<OpenLibrary, String> colBookISBN10;
    @FXML
    private TableColumn<OpenLibrary, String> colBookISBN13;
    @FXML
    private Button btnRequestBook;
    @FXML
    private Label lblBookRequestInfo;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private Button btnBooksSearch;
    @FXML
    private TextField txfBookSearchInput;
    @FXML
    private RadioButton rbBookSearchTitle;
    @FXML
    private ToggleGroup bookSearchTypeGroup;
    @FXML
    private RadioButton rbBookSearchAuthor;
    @FXML
    private RadioButton rbBookSearchISBN;
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
                
                // Getting the BookRequest stage
                stageBookRequest = (Stage) anchorPaneBackground.getScene().getWindow();    
            }
        });    
    }  
    
    // Method to set the currentUser so that it can be used for user related searches
    public void currentUser(String userid){
        currentUser = userid;
    }    

    private void displaySearchedBooks(ArrayList<OpenLibrary> fetchedBooks){   
        
        // Clearing TableView
        tblRequestBook.getItems().clear();    

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblRequestBook.setPlaceholder(new Label("No books found"));
        }else{        
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object  
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
            colBookISBN10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
            colBookISBN13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<OpenLibrary> books = FXCollections.observableArrayList(fetchedBooks);
            tblRequestBook.setItems(books);
        }// END if-else - any found books
    }    
    
    @FXML
    private void btnBooksSearchClicked(MouseEvent event) {
        
        // Clearing TableView
        tblRequestBook.getItems().clear();
        
        // Searches what the user wants to search according to the type of search - default title
        if(rbBookSearchTitle.isSelected()){
            // Seeing if the user entered in anything to search. If not, new table label saying so
            if(txfBookSearchInput.getText().isEmpty()){
                tblRequestBook.setPlaceholder(new Label("Enter in a book title to search for"));
            }else{
                // Initializing a temporary ArrayList to store the results found
                ArrayList<OpenLibrary> resultTitle = olBooks.searchTitle(txfBookSearchInput.getText());
                
                displaySearchedBooks(resultTitle);
            }// END if-else - book search by Title
            
        }else if(rbBookSearchAuthor.isSelected()){
            // Seeing if the user entered in anything to search. If not, new table label saying so
            if(txfBookSearchInput.getText().isEmpty()){
                tblRequestBook.setPlaceholder(new Label("Enter in a book author to search for"));
            }else{
                ArrayList<OpenLibrary> resultAuthor = olBooks.searchAuthor(txfBookSearchInput.getText());
                
                displaySearchedBooks(resultAuthor);              
            }// END if-else - book search by Author
            
        }else if(rbBookSearchISBN.isSelected()){
            // Seeing if the user entered in anything to search. If not, new table label saying so
            if(txfBookSearchInput.getText().isEmpty()){
                tblRequestBook.setPlaceholder(new Label("Enter in a book ISBN10/ISBN13 to search for"));
            }else{
                ArrayList<OpenLibrary> resultISBN = olBooks.searchISBN(txfBookSearchInput.getText());
                
                displaySearchedBooks(resultISBN);               
            }// END if-else - book search by ISBN
            
        }
    }

    @FXML
    private void btnRequestBookClicked(MouseEvent event) {
        
        // Checking to see if the user selected something in the table
        if(tblRequestBook.getSelectionModel().getSelectedItem() != null){
            // Getting selected Book
            OpenLibrary book = tblRequestBook.getSelectionModel().getSelectedItem();
            
            String title = book.getTitle();
            String authors = book.getAuthors();
            String isbn10 = book.getIsbn10();
            String isbn13 = book.getIsbn13();
            
            // Adding BookRequest to database
            libActions.requestBook(currentUser, title, authors, isbn10, isbn13);
        }else{
            errorHandler.requestBookError(stageBookRequest);
        }// END if-else - book request
        
    }

    // Below is the HelpIcon event - displays the respective help dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.libraryRequestBookHelp(stageBookRequest);
    }
    
}
