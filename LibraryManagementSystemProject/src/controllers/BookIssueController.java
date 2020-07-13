/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import librarymanagementsystemproject.Books;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class BookIssueController implements Initializable {
    
    // Initializing necessary ArrayLists
    private ArrayList<RegisteredUsers> registeredUsers = new ArrayList<>();
    private ArrayList<Books> booksAvailable = new ArrayList<>();

    // Initializing necessary Objects
    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling();
    private HelpHandling helpHandler = new HelpHandling();
    
    private LocalDate currDate = LocalDate.now();
    private LocalDate returnDate = libActions.getReturnDate();    
    private Stage stageBookIssue = null;
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneMembers;
    @FXML
    private Button btnMemberSearch;
    @FXML
    private TextField txfMemberSearchInput;
    @FXML
    private AnchorPane anchorPaneBooks;
    @FXML
    private TextField txfBookSearchInput;
    @FXML
    private AnchorPane anchorPaneIssue;
    @FXML
    private Button btnBookIssue;
    @FXML
    private TableView<RegisteredUsers> tblMembers;
    @FXML
    private TableView<Books> tblBooks;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberID;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberFirstName;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberSurname;
    @FXML
    private TableColumn<Books, String> colBookID;
    @FXML
    private TableColumn<Books, String> colBookTitle;
    @FXML
    private TableColumn<Books, String> colBookAuthors;
    @FXML
    private Button btnBookSearch;
    @FXML
    private Label lblDateReturn;
    @FXML
    private Label lblDateCurrent;
    @FXML
    private Label lblDateCurrentDate;
    @FXML
    private Label lblDateReturnDate;
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
                
                // Getting the BookIssue stage
                stageBookIssue = (Stage) anchorPaneBackground.getScene().getWindow();    
                
                // Clearing the TableViews
                tblMembers.getItems().clear();
                tblBooks.getItems().clear();

                // Fetching necessary data from PostgreDB - All Members and Available Books
                registeredUsers = lib.fetchAllMembers();  
                booksAvailable = lib.fetchAvailableBooks();
                
                // Displaying the data fetched            
                displayMembers(registeredUsers);
                displayBooks(booksAvailable);
                
                // Displaying the current date and return date of books issued that day
                lblDateCurrentDate.setText(currDate.toString());
                lblDateReturnDate.setText(returnDate.toString());
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
    
    private void displayBooks(ArrayList<Books> fetchedBooks){   
        
        // Clearing TableView
        tblBooks.getItems().clear();   

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblBooks.setPlaceholder(new Label("No available books"));
        }else{        
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<Books> books = FXCollections.observableArrayList(fetchedBooks);
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
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<Books> allBooks = lib.fetchAvailableBooks();
            displayBooks(allBooks);
        }else{
            // Initializing a temporary ArrayList to store all found books
            ArrayList<Books> books = new ArrayList<>();
            books.clear();
            
            // Searching through all the available books and finding a match
            for(Books book: booksAvailable){
                if(book.getBookid().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    books.add(book);
                }else if(book.getTitle().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    books.add(book);
                }else if(book.getAuthors().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    books.add(book);
                }// END if-else - search filter
            }// END loop
            
            // Display a label in the TableView is no result was found, else displaying all books found
            if(books.isEmpty()){
                tblBooks.setPlaceholder(new Label("Did not find any books"));   
            }else{
                displayBooks(books);
            }// END if-else - checking results     
        }// END if-else - anything to search 
    }

    @FXML
    private void btnBookIssueClicked(MouseEvent event) { 
        
        boolean flagSelectedMember = false;
        boolean flagSelectedBook = false;
        
        RegisteredUsers selectedUser = null;
        Books selectedBook = null;
        
        
        if(tblMembers.getSelectionModel().getSelectedItem() != null){
            // Getting selected Member
            selectedUser = tblMembers.getSelectionModel().getSelectedItem();
            flagSelectedMember = true;
        }// END if - checking that a Member was selected
        
        if(tblBooks.getSelectionModel().getSelectedItem() != null){
            // Getting selected Book
            selectedBook = tblBooks.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
        }// END if = checking that a Book was selected
        
        if(flagSelectedBook & flagSelectedMember){
            // Issuing book to member
            libActions.issueBook(selectedUser, selectedBook, currDate, returnDate);

            // Resetting search input - Book & Member
            txfBookSearchInput.setText("");
            txfMemberSearchInput.setText("");

            // Updating the BooksTable since a book was taken out - one less book available of the book taken out
            booksAvailable = lib.fetchAvailableBooks();
            displayBooks(booksAvailable);
            
        }else if(!flagSelectedBook & flagSelectedMember){
            errorHandler.bookIssueError(stageBookIssue, "book");
        }else if(flagSelectedBook & !flagSelectedMember){
            errorHandler.bookIssueError(stageBookIssue, "member");
        }else{
            errorHandler.bookIssueError(stageBookIssue, "both");
        }// END if-else - checking that both a Book and Member was select. Else, displaying correct warning message   
    }

    
    // Below are all of the HelpIcon events - displays respected Help Dialog
    @FXML
    private void imHelpMemberClicked(MouseEvent event) {
        helpHandler.booksIssueMemberHelp(stageBookIssue);
    }

    @FXML
    private void imHelpBooksClicked(MouseEvent event) {
        helpHandler.booksIssueBookHelp(stageBookIssue);
    }

    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.booksIssueHelp(stageBookIssue);
    }
    
}
