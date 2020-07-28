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
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class ListAvailableBooksController implements Initializable {

    // Initializing necessary ArrayLists
    private ArrayList<Books> availableBooks = new ArrayList<>();
    
    private Stage stageAvailableBooks = new Stage();
    
    // Instantiating necessary Objects
    private Library lib = new Library();
    private HelpHandling helpHandler = new HelpHandling();
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOutput;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private TableColumn<Books, String> colBookID;
    @FXML
    private TableColumn<Books, String> colBookTitle;
    @FXML
    private TableColumn<Books, String> colBookAuthors;
    @FXML
    private TableColumn<Books, Integer> colBookAmount;
    @FXML
    private TableView<Books> tblAvailableBooks;
    @FXML
    private TableColumn<Books, String> colBookGenres;
    @FXML
    private Button btnBooksSearch;
    @FXML
    private TextField txfBookSearchInput;
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
                
                // Getting the AvailableBooks stage
                stageAvailableBooks = (Stage) anchorPaneBackground.getScene().getWindow();   
                
                // Clearing TableView
                tblAvailableBooks.getItems().clear();
                
                // Fetching data from PostgreDB - all AvailableBooks
                availableBooks = lib.fetchAvailableBooks();  
                
                // Displaying data
                booksDisplay(availableBooks);                            
            }
        });   
    } 

    // Shows all available books by default
    private void booksDisplay(ArrayList<Books> fetchedBooks){  
        
        // Clearing TableView
        tblAvailableBooks.getItems().clear();

        // If nothing was found, display all . Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblAvailableBooks.setPlaceholder(new Label("No available books"));
        }else{  
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object       
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
            colBookAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<Books> books = FXCollections.observableArrayList(fetchedBooks);
            tblAvailableBooks.setItems(books);
        }// END if-else - any found books
    }
    
    
    @FXML
    private void btnBooksFetchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblAvailableBooks.getItems().clear();
        
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<Books> allAvailableBooks = lib.fetchAvailableBooks();
            booksDisplay(allAvailableBooks);
        }else{
            // Initializing temporary ArrayList to store results
            ArrayList<Books> avaiableBooksSearchResults = new ArrayList<>();
            avaiableBooksSearchResults.clear();

            for(Books book: availableBooks){
                if(book.getTitle().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    avaiableBooksSearchResults.add(book);
                }else if(book.getAuthors().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    avaiableBooksSearchResults.add(book);
                }else if(book.getIsbn10().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    avaiableBooksSearchResults.add(book);
                }else if(book.getIsbn13().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    avaiableBooksSearchResults.add(book);
                }else if(book.getAuthors().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    avaiableBooksSearchResults.add(book);
                }// END if-else - search filter
            }// END loop
            
            // Displaying label in TableView if no results were found. Else displaying results
            if(avaiableBooksSearchResults.isEmpty()){
                tblAvailableBooks.setPlaceholder(new Label("No Books found."));
            }else{
                booksDisplay(avaiableBooksSearchResults);
            }// END if-else - checking results
            
        }// END if-else - anything to search   
    }

    
    // Below is the HelpIcon event - displays respective help dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.libraryAvailableBooksHelp(stageAvailableBooks);
    }
    
}
