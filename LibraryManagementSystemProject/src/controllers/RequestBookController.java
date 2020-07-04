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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.OpenLibrary;
import librarymanagementsystemproject.OpenLibraryBooks;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class RequestBookController implements Initializable {
    
    private String currentUser = "";
    
    private LibraryActions libActions = new LibraryActions();
    private OpenLibraryBooks olBooks = new OpenLibraryBooks();

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    // Method to set the currentUser so that it can be used for user related searches
    public void currentUser(String userid){
        currentUser = userid;
    }    

    @FXML
    private void btnBooksSearchClicked(MouseEvent event) {
        
        tblRequestBook.getItems().clear();
        
        // Searches what the user wants to search according to the type of search - default title
        if(rbBookSearchTitle.isSelected()){
            // Seeing if the user entered in anything to search. If not, new table label saying so
            if(txfBookSearchInput.getText().isEmpty()){
                tblRequestBook.setPlaceholder(new Label("Enter in a book title to search for"));
            }else{
                ArrayList<OpenLibrary> resultTitle = olBooks.searchTitle(txfBookSearchInput.getText());
                
                if(resultTitle.isEmpty()){
                    tblRequestBook.setPlaceholder(new Label("No results found"));
                }else{
                    colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                    colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
                    colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
                    colBookISBN10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
                    colBookISBN13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));

                    ObservableList<OpenLibrary> results = FXCollections.observableArrayList(resultTitle);
                    tblRequestBook.setItems(results);  
                }
            }
            
        }else if(rbBookSearchAuthor.isSelected()){
            // Seeing if the user entered in anything to search. If not, new table label saying so
            if(txfBookSearchInput.getText().isEmpty()){
                tblRequestBook.setPlaceholder(new Label("Enter in a book author to search for"));
            }else{
                ArrayList<OpenLibrary> resultAuthor = olBooks.searchAuthor(txfBookSearchInput.getText());
                
                if(resultAuthor.isEmpty()){
                    tblRequestBook.setPlaceholder(new Label("No results found"));
                }else{
                    colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                    colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
                    colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
                    colBookISBN10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
                    colBookISBN13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));

                    ObservableList<OpenLibrary> results = FXCollections.observableArrayList(resultAuthor);
                    tblRequestBook.setItems(results);  
                }              
            }
            
        }else if(rbBookSearchISBN.isSelected()){
            // Seeing if the user entered in anything to search. If not, new table label saying so
            if(txfBookSearchInput.getText().isEmpty()){
                tblRequestBook.setPlaceholder(new Label("Enter in a book ISBN10/ISBN13 to search for"));
            }else{
                ArrayList<OpenLibrary> resultISBN = olBooks.searchISBN(txfBookSearchInput.getText());
                
                if(resultISBN.isEmpty()){
                    tblRequestBook.setPlaceholder(new Label("No results found"));
                }else{
                    colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                    colBookAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
                    colBookGenres.setCellValueFactory(new PropertyValueFactory<>("genres"));
                    colBookISBN10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
                    colBookISBN13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));

                    ObservableList<OpenLibrary> results = FXCollections.observableArrayList(resultISBN);
                    tblRequestBook.setItems(results);  
                }                  
            }
            
        }
    }

    @FXML
    private void btnRequestBookClicked(MouseEvent event) {
        
        if(tblRequestBook.getSelectionModel().getSelectedItem() != null){
            OpenLibrary book = tblRequestBook.getSelectionModel().getSelectedItem();
            
            String title = book.getTitle();
            String authors = book.getAuthors();
            String isbn10 = book.getIsbn10();
            String isbn13 = book.getIsbn13();
            
            libActions.requestBook(currentUser, title, authors, isbn10, isbn13);
        }
        
    }
    
    
    
}
