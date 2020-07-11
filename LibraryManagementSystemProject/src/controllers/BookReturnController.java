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
import librarymanagementsystemproject.BooksTakenOut;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class BookReturnController implements Initializable {
    
    private ArrayList<RegisteredUsers> registeredUsers = new ArrayList<>();
    private ArrayList<BooksTakenOut> takeouts = new ArrayList<>();

    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private ErrorHandling errorHandler = new ErrorHandling();
    
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
    private Label lblMember;
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
    private Label lblBooks;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){  
                
                // Getting the Member stage
                stageBookReturn = (Stage) anchorPaneBackground.getScene().getWindow();    
                
                tblMembers.getItems().clear();
                tblBooks.getItems().clear();

                registeredUsers = lib.fetchTakeOutBookMembers();
                takeouts = lib.fetchTakenOutBooks();
                
                                
                displayMembers(registeredUsers);
                displayBooks(takeouts);
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
    
    private void displayBooks(ArrayList<BooksTakenOut> fetchedBooks){

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblBooks.setPlaceholder(new Label("No available books"));
        }else{        
            colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutID"));
            colTakeoutBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colTakeoutUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colTakeoutDate.setCellValueFactory(new PropertyValueFactory<>("dateTakeout"));
            colTakeoutReturnDate.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));

            ObservableList<BooksTakenOut> books = FXCollections.observableArrayList(fetchedBooks);
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
        
        // If search text field is empty, displays all takeouts. If not, finds a takeout similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<BooksTakenOut> allTakenOutBooks = lib.fetchTakenOutBooks();
            displayBooks(allTakenOutBooks);
        }else{
            ArrayList<BooksTakenOut> takeoutsSearched = new ArrayList<>();
            takeoutsSearched.clear();
            
            for(BooksTakenOut book: takeouts){
                if(book.getBookID().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }else if(book.getUserID().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }else if(book.getTakeoutID().toLowerCase().contains(txfBookSearchInput.getText().toLowerCase())){
                    takeoutsSearched.add(book);
                }
            }
            
            if(takeoutsSearched.isEmpty()){
                tblMembers.setPlaceholder(new Label("Did not find any takeouts"));   
            }else{
                displayBooks(takeoutsSearched);
            }
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnBookIssueClicked(MouseEvent event) {
        boolean flagSelectedBook = false;
        BooksTakenOut selectedBook = null;
        
        if(tblBooks.getSelectionModel().getSelectedItem() != null){
            selectedBook = tblBooks.getSelectionModel().getSelectedItem();
            flagSelectedBook = true;
        }
        
        if(flagSelectedBook){
            libActions.returnBook(selectedBook);
        }else{
            errorHandler.bookReturnError(stageBookReturn);
        }     
        
        // Filtering - updating the BooksTable since a book was returned - one less book in the table
        ArrayList<BooksTakenOut> takeoutBooksUpdated = lib.fetchTakenOutBooks();
        takeouts.clear();
        
        for(BooksTakenOut book: takeoutBooksUpdated){
            takeouts.add(book);
        }
        displayBooks(takeouts);
    }

    @FXML
    private void tblMembersClicked(MouseEvent event) {
        if(tblMembers.getSelectionModel().getSelectedItem() != null){
            RegisteredUsers selectedUser = tblMembers.getSelectionModel().getSelectedItem();   
            ArrayList<BooksTakenOut> userTakeoutBooks = lib.fetchTakenOutBooks(selectedUser.getUserID());
            displayBooks(userTakeoutBooks);         
        }
        
    }
    
}
