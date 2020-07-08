/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.Members;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class StaffController implements Initializable {

    private Stage stageStaff = null;  
    private Members mem = new Members();
    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    
    private String currentUser = "";
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneSideMenu;
    @FXML
    private BorderPane bpSettings;
    @FXML
    private ImageView lblAccount;
    @FXML
    private ImageView lblSettings;
    @FXML
    private Label lblWelcomeBack;
    @FXML
    private Label lblMemberName;
    @FXML
    private Label lblLibrary;
    @FXML
    private HBox hBoxBooksAvailable;
    @FXML
    private ImageView imBooksAvailable;
    @FXML
    private Label lblBooksAvailable;
    @FXML
    private HBox hBoxBooksList;
    @FXML
    private ImageView imBookList;
    @FXML
    private Label lblBooksList;
    @FXML
    private HBox hBoxBooksRequest;
    @FXML
    private ImageView imBookRequest;
    @FXML
    private Label lblBooksRequest;
    @FXML
    private HBox hBoxStaffMemberReturnDates;
    @FXML
    private ImageView imReturnDates;
    @FXML
    private Label lblMemberReturnDates;
    @FXML
    private HBox hBoxStaffMemberTakenOut;
    @FXML
    private ImageView imTakenOut;
    @FXML
    private Label lblMemberTakenOut;
    @FXML
    private Label lblBooks;
    @FXML
    private HBox hBoxIssueBooks;
    @FXML
    private ImageView imIssueBook;
    @FXML
    private Label lblIssueBook;
    @FXML
    private HBox hBoxReturnBook;
    @FXML
    private ImageView imReturnBook;
    @FXML
    private Label lblReturnBook;
    @FXML
    private HBox hBoxFixBook;
    @FXML
    private ImageView imFixBook;
    @FXML
    private Label lblFixBook;
    @FXML
    private HBox hBoxOutstandingBooks;
    @FXML
    private ImageView imOutstandingBooks;
    @FXML
    private Label lblOutstandingBooks;
    @FXML
    private HBox hBoxViewRequestedBooks;
    @FXML
    private ImageView imRequestedBooks;
    @FXML
    private Label lblRequestedBooks;
    @FXML
    private BorderPane bpMain;
    @FXML
    private ImageView imExit;
    @FXML
    private Label lblStaff;
    @FXML
    private HBox hBoxStaffAdd;
    @FXML
    private ImageView imStaffAdd;
    @FXML
    private Label lblStaffAdd;
    @FXML
    private HBox hBoxStaffRemove;
    @FXML
    private ImageView imStaffRemove;
    @FXML
    private Label lblStaffRemove;
    @FXML
    private HBox hBoxStaffView;
    @FXML
    private ImageView imStaffView;
    @FXML
    private Label lblStaffView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Platform.runLater(new Runnable() {
            @Override
            public void run(){            
                
                // Getting the Member stage
                stageStaff = (Stage) anchorPaneBackground.getScene().getWindow();   
                
                RegisteredUsers user = mem.getMemberByUserID(currentUser);
                String userName = user.getFirstName();
                lblMemberName.setText(userName);
                
            }
        });
    } 
    
    public void currentUser(String userid){
        currentUser = userid;
    }

    @FXML
    private void lblAccountClicked(MouseEvent event) {
    }

    @FXML
    private void lblSettingsClicked(MouseEvent event) {
    }

    @FXML
    private void hBoxBooksAvailableClicked(MouseEvent event) {
    }

    @FXML
    private void hBoxBooksListClicked(MouseEvent event) {
    }

    @FXML
    private void lblBooksRequestClicked(MouseEvent event) {
    }

    @FXML
    private void lblMemberReturnDatesClicked(MouseEvent event) {
    }

    @FXML
    private void lblMemberTakenOutClicked(MouseEvent event) {
    }

    @FXML
    private void lblExitClicked(MouseEvent event) {
        // Closing Member stage and going to the SignIn stage if the user closes the window
        try{
            // Loading root of Member. Getting a stage so that it can be viewed and the user can create their new accont
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stages/SignIn.fxml"));
            Parent root = (Parent) loader.load();

            Scene signin = new Scene(root);
            Stage stageSignIn = new Stage();            
            stageSignIn.initStyle(StageStyle.UNDECORATED);
            stageSignIn.setScene(signin);

            stageSignIn.show();
            stageStaff.hide();
        }catch(IOException err){
            err.printStackTrace();
        }
    }
    
}
