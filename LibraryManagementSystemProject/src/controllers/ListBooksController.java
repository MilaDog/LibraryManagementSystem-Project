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
import librarymanagementsystemproject.Library;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class ListBooksController implements Initializable {

    private Library lib = new Library();
    
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tblAvailableBooks.getItems().clear();
        
        ArrayList<Books> allBooks = lib.fetchBooks();  
        defaultDisplay(allBooks);
    }    

    // Shows all available books by default
    private void defaultDisplay(ArrayList<Books> fetchedBooks){

        // If nothing was found, say to. Else, display what was found
        if(fetchedBooks.isEmpty()){            
            tblAvailableBooks.setPlaceholder(new Label("No available books"));
        }else{        
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
            colBookAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            ObservableList<Books> books = FXCollections.observableArrayList(fetchedBooks);
            tblAvailableBooks.setItems(books);
        }// END if-else - any found books
    }
    
    
    @FXML
    private void btnBooksFetchClicked(MouseEvent event) {   
        
        tblAvailableBooks.getItems().clear();
        
        // If search text field is empty, displays all books. If not, finds a book similar to what is being searched.
        if(txfBookSearchInput.getText().isEmpty()){
            ArrayList<Books> allBooks = lib.fetchAvailableBooks();
            defaultDisplay(allBooks);
        }else{
            ArrayList<Books> availableBooks = lib.fetchBook(txfBookSearchInput.getText().toLowerCase());  

            // If nothing was found, say to. Else, display what was found
            if(availableBooks.isEmpty()){            
                tblAvailableBooks.setPlaceholder(new Label("No available books"));
            }else{        
                colBookID.setCellValueFactory(new PropertyValueFactory<>("bookid"));
                colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
                colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
                colBookAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

                ObservableList<Books> books = FXCollections.observableArrayList(availableBooks);
                tblAvailableBooks.setItems(books);
            }// END if-else - any found books
        }// END if-else - anything to search   
    }
    
}
