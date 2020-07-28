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
import librarymanagementsystemproject.BooksTakenOut;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class BookReturnController implements Initializable {
    
    // Initializing necessary ArrayLists
    private ArrayList<RegisteredUsers> registeredUsers = new ArrayList<>();
    private ArrayList<BooksTakenOut> takeouts = new ArrayList<>();

    // Instantiating necessary Objects
    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageBookReturn = null;

    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneMembers;
    @FXML
    private Button btnMemberSearch;
    @FXML
    private TextField txfMemberSearchInput;
    @FXML
    private TableView<RegisteredUsers> tblMembers;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberID;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberFirstName;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberSurname;
    @FXML
    private AnchorPane anchorPaneBooks;
    @FXML
    private Button btnBookSearch;
    @FXML
    private TextField txfBookSearchInput;
    @FXML
    private TableView<BooksTakenOut> tblBooks;
    @FXML
    private AnchorPane anchorPaneIssue;
    @FXML
    private Button btnBookReturn;
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
    private ImageView imHelpMember;
    @FXML
    private ImageView imHelpBooks;
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
                
                // Getting the BookReturn stage
                stageBookReturn = (Stage) anchorPaneBackground.getScene().getWindow();    
                
                // Clearing TableViews
                tblMembers.getItems().clear();
                tblBooks.getItems().clear();

                // Fetching necessary data from PostgreDB - All Members and all TakenOut Books
                registeredUsers = lib.fetchTakeOutBookMembers();
                takeouts = lib.fetchTakenOutBooks();
                
                // Display fetched data
                displayMembers(registeredUsers);
                displayBooks(takeouts);
            }
        }); 
    }         

    private void displayMembers(ArrayList<RegisteredUsers> fetchedUsers){   
        
        // Clearing TableView
        tblMembers.getItems().clear();   

        // If nothing was found, display all . Else, display what was found
        if(fetchedUsers.isEmpty()){            
            tblMembers.setPlaceholder(new Label("There are no Registered Users"));
        }else{      
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object  
            colMemberID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colMemberFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colMemberSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<RegisteredUsers> allUsers = FXCollections.observableArrayList(fetchedUsers);
            tblMembers.setItems(allUsers);
        }// END if-else - any found books
        
    }
    
    private void displayBooks(ArrayList<BooksTakenOut> fetchedBooks){   
        
        // Clearing TableView
        tblBooks.getItems().clear();   

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblBooks.setPlaceholder(new Label("No available books"));
        }else{  
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object      
            colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutID"));
            colTakeoutBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colTakeoutUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colTakeoutDate.setCellValueFactory(new PropertyValueFactory<>("dateTakeout"));
            colTakeoutReturnDate.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<BooksTakenOut> books = FXCollections.observableArrayList(fetchedBooks);
            tblBooks.setItems(books);
        }// END if-else - any found books
    }  

    @FXML
    private void btnMemberSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblMembers.getItems().clear();        
        
        // If search text field is empty, displays all registered users. If not, finds a user similar to what is being searched.
        if(txfMemberSearchInput.getText().isEmpty()){
            ArrayList<RegisteredUsers> allUsers = lib.fetchAllMembers();
            displayMembers(allUsers);
        }else{
            // Initializing a temporary ArrayList to store all found Members
            ArrayList<RegisteredUsers> users = new ArrayList<>();
            users.clear();
            
            // Searching through all the registered users and finding a match
            for(RegisteredUsers user: registeredUsers){
                if(user.getUserID().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }else if(user.getFirstName().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }else if(user.getSurname().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }// END if-else - search filter
            }// END loop
            
            // Display a label in the TableView is no result was found, else displaying all members found
            if(users.isEmpty()){
                tblMembers.setPlaceholder(new Label("Did not find any member"));   
            }else{
                displayMembers(users);
            }// END if-else - checking results             
        }// END if-else - anything to search 
    }

    @FXML
    private void btnBookSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblBooks.getItems().clear();        
        
        // If search text field is empty, displays all takeouts. If not, finds a takeout similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<BooksTakenOut> allTakenOutBooks = lib.fetchTakenOutBooks();
            displayBooks(allTakenOutBooks);
        }else{
            // Initializing a temporary ArrayList to store all found Takeouts
            ArrayList<BooksTakenOut> takeoutsSearched = new ArrayList<>();
            takeoutsSearched.clear();
            
            // Searching through all the takenouts and finding a match
            for(BooksTakenOut book: takeouts){
                if(book.getBookID().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }else if(book.getUserID().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }else if(book.getTakeoutID().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }// END if-else - search filter
            }// END loop
            
            // Display a label in the TableView is no result was found, else displaying all takeouts found
            if(takeoutsSearched.isEmpty()){
                tblMembers.setPlaceholder(new Label("Did not find any takeouts"));   
            }else{
                displayBooks(takeoutsSearched);
            }// END if-else - checking results             
        }// END if-else - anything to search 
    }

    @FXML
    private void btnBookIssueClicked(MouseEvent event) {
        boolean flagSelectedBook = false;
        BooksTakenOut selectedBook = null;
        
        if(tblBooks.getSelectionModel().getSelectedItem() != null){
            // Getting selected Takeout
            selectedBook = tblBooks.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
        }// END if - checking that a Takeout was selected
        
        if(flagSelectedBook){
            // Returning book
            libActions.returnBook(selectedBook);
        }else{
            errorHandler.bookReturnError(stageBookReturn);
        }// END if-else - checking that a Takeout was select. Else, displaying correct warning message

        // Resetting search input
        txfBookSearchInput.setText("");     
        
        // Updating the BooksTable since a book was returned - one less book in the table
        takeouts = lib.fetchTakenOutBooks();
        displayBooks(takeouts);
    }

    @FXML
    private void tblMembersClicked(MouseEvent event) {
        // Checking if a Member was select. If so, displays all of that member's takeout books in the TakenOut TableView
        if(tblMembers.getSelectionModel().getSelectedItem() != null){
            RegisteredUsers selectedUser = tblMembers.getSelectionModel().getSelectedItem();   
            ArrayList<BooksTakenOut> userTakeoutBooks = lib.fetchTakenOutBooks(selectedUser.getUserID());
            displayBooks(userTakeoutBooks);         
        }
        
    }

    
    // Below are all of the HelpIcon events - displays respected Help Dialog

    @FXML
    private void imHelpMemberClicked(MouseEvent event) {
        helpHandler.booksIssueMemberHelp(stageBookReturn);
    }

    @FXML
    private void imHelpBooksClicked(MouseEvent event) {
        helpHandler.booksIssueBookHelp(stageBookReturn);
    }

    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.booksIssueHelp(stageBookReturn);
    }
    
}
