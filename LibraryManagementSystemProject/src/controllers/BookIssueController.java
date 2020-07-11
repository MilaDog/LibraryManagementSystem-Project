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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagementsystemproject.Books;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class BookIssueController implements Initializable {
    
    private ArrayList<RegisteredUsers> registeredUsers = new ArrayList<>();
    private ArrayList<Books> booksAvailable = new ArrayList<>();

    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling();

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
    private Label lblMember;
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
    private Label lblBooks;
    @FXML
    private Label lblDateReturn;
    @FXML
    private Label lblDateCurrent;
    @FXML
    private Label lblDateCurrentDate;
    @FXML
    private Label lblDateReturnDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){  
                
                // Getting the Member stage
                stageBookIssue = (Stage) anchorPaneBackground.getScene().getWindow();    
                
                tblMembers.getItems().clear();
                tblBooks.getItems().clear();

                registeredUsers = lib.fetchAllMembers();  
                booksAvailable = lib.fetchAvailableBooks();
                
                                
                displayMembers(registeredUsers);
                displayBooks(booksAvailable);
                
                // Displaying the current date and return date of books issued that day
                lblDateCurrentDate.setText(currDate.toString());
                lblDateReturnDate.setText(returnDate.toString());
            }
        }); 
    }         

    private void displayMembers(ArrayList<RegisteredUsers> fetchedUsers){

        // If nothing was found, display all . Else, display what was found
        if(fetchedUsers.isEmpty()){            
            tblMembers.setPlaceholder(new Label("There are no Registered Users"));
        }else{        
            colMemberID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colMemberFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colMemberSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));

            ObservableList<RegisteredUsers> allUsers = FXCollections.observableArrayList(fetchedUsers);
            tblMembers.setItems(allUsers);
        }// END if-else - any found books
        
    }
    
    private void displayBooks(ArrayList<Books> fetchedBooks){

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblBooks.setPlaceholder(new Label("No available books"));
        }else{        
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));

            ObservableList<Books> books = FXCollections.observableArrayList(fetchedBooks);
            tblBooks.setItems(books);
        }// END if-else - any found books
    }    
    
    @FXML
    private void btnMemberSearchClicked(MouseEvent event) {   
        
        tblMembers.getItems().clear();        
        
        // If search text field is empty, displays all registered users. If not, finds a user similar to what is being searched.
        if(txfMemberSearchInput.getText().isEmpty()){
            ArrayList<RegisteredUsers> allUsers = lib.fetchAllMembers();
            displayMembers(allUsers);
        }else{
            ArrayList<RegisteredUsers> users = new ArrayList<>();
            users.clear();
            
            for(RegisteredUsers user: registeredUsers){
                if(user.getUserID().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }else if(user.getFirstName().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }else if(user.getSurname().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }
            }
            
            if(users.isEmpty()){
                tblMembers.setPlaceholder(new Label("Did not find any member"));   
            }else{
                displayMembers(users);
            }
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnBookSearchClicked(MouseEvent event) {   
        
        tblBooks.getItems().clear();        
        
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
                tblMembers.setPlaceholder(new Label("Did not find any books"));   
            }else{
                displayBooks(books);
            }
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnBookIssueClicked(MouseEvent event) { 
        
        boolean flagSelectedMember = false;
        boolean flagSelectedBook = false;
        
        RegisteredUsers selectedUser = null;
        Books selectedBook = null;
        
        if(tblMembers.getSelectionModel().getSelectedItem() != null){
            selectedUser = tblMembers.getSelectionModel().getSelectedItem();
            flagSelectedMember = true;
        }
        if(tblBooks.getSelectionModel().getSelectedItem() != null){
            selectedBook = tblBooks.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
        }
        
        if(flagSelectedBook & flagSelectedMember){
            libActions.issueBook(selectedUser, selectedBook, currDate, returnDate);

            // Filtering - updating the BooksTable since a book was taken out - one less book available of the book taken out
            ArrayList<Books> booksAvailableUpdated = lib.fetchAvailableBooks();
            booksAvailable.clear();

            for(Books book: booksAvailableUpdated){
                booksAvailable.add(book);
            }
            displayBooks(booksAvailable);
        }else if(!flagSelectedBook & flagSelectedMember){
            errorHandler.bookIssueError(stageBookIssue, "book");
        }else if(flagSelectedBook & !flagSelectedMember){
            errorHandler.bookIssueError(stageBookIssue, "member");
        }else{
            errorHandler.bookIssueError(stageBookIssue, "both");
        }
        

    }
    
}
