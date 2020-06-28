/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import librarymanagementsystemproject.Books;
import librarymanagementsystemproject.BooksTakenOut;
import librarymanagementsystemproject.BooksTakenOutDetails;
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class TakenOutController implements Initializable {

    private String currentUser = "";
    
    private Library lib = new Library();
    
    private Books fetchedBook = null;
    private ArrayList<BooksTakenOut> takenOutBooks = null;
    private ArrayList<BooksTakenOutDetails> takenOutBooksDetails = null;
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOutput;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private TableColumn<BooksTakenOutDetails, String> colBookID;
    @FXML
    private TableColumn<BooksTakenOutDetails, String> colBookTitle;
    @FXML
    private TableColumn<BooksTakenOutDetails, String> colBookAuthors;
    @FXML
    private Button btnBooksSearch;
    @FXML
    private TextField txfBookSearchInput;
    @FXML
    private TableColumn<BooksTakenOutDetails, String> colTakeoutID;
    @FXML
    private TableView<BooksTakenOutDetails> tblTakenOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        tblTakenOut.getItems().clear();
        
        takenOutBooks = lib.fetchTakenOutBooks(this.currentUser);
        
        if(takenOutBooks.isEmpty()){
            System.out.println("yebo empty");
        }else{
            for(BooksTakenOut takeout: takenOutBooks){
                fetchedBook = lib.fetchBookByID(takeout.getBookID());
                
                String takeoutID = takeout.getTakeoutID();
                String bookID = fetchedBook.getBookid();
                String title = fetchedBook.getTitle();
                String authors = fetchedBook.getAuthors();
                String genres = fetchedBook.getGenres();
                String isbn10 = fetchedBook.getIsbn10();
                String isbn13 = fetchedBook.getIsbn13();
                int amount = fetchedBook.getAmount();

                takenOutBooksDetails.add(new BooksTakenOutDetails(takeoutID, bookID, title, authors, genres, isbn10, isbn13, amount));     
            }
        }       
        defaultDisplay(takenOutBooksDetails);
        
    }
    
    public void currentUser(String userid){
        currentUser = userid;
    }   
    
    
    // Shows all available books by default
    private void defaultDisplay(ArrayList<BooksTakenOutDetails> fetchedTakeOuts){

        // If nothing was found, say so. Else, display what was found
        if(fetchedTakeOuts.isEmpty()){            
            tblTakenOut.setPlaceholder(new Label("You have not taken a book out yet."));
        }else{        
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            colTakeoutID.setCellValueFactory(new PropertyValueFactory<>("takeoutid"));

            ObservableList<BooksTakenOutDetails> takeOuts = FXCollections.observableArrayList(fetchedTakeOuts);
            tblTakenOut.setItems(takeOuts);
        }// END if-else - any found books
    }
    
    
    @FXML
    private void btnBooksFetchClicked(MouseEvent event) {   
//        
//        tblAvailableBooks.getItems().clear();
//        
//        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
//        if(txfBookSearchInput.getText().equalsIgnoreCase("")){
//            defaultDisplay(availableBooks);
//        }else{
//            ArrayList<Books> availableBooks = lib.fetchBook(txfBookSearchInput.getText().toLowerCase());  
//
//            // If nothing was found, say to. Else, display what was found
//            if(availableBooks.isEmpty()){            
//                tblAvailableBooks.setPlaceholder(new Label("No available books"));
//            }else{        
//                colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
//                colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//                colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
//                colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
//                colBookAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
//
//                ObservableList<Books> books = FXCollections.observableArrayList(availableBooks);
//                tblAvailableBooks.setItems(books);
//            }// END if-else - any found books
//        }// END if-else - anything to search   
    }
    
}
