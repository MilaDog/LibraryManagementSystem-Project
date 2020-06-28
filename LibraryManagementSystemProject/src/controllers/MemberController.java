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

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class MemberController implements Initializable {

    private String currentUser = "";
    
    private Library lib = new Library();
    private Stage stageMember = null;
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneSideMenu;
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
    private HBox hBoxMemberReturnDates;
    @FXML
    private ImageView imReturnDates;
    @FXML
    private Label lblMemberReturnDates;
    @FXML
    private HBox hBoxMemberTakenOut;
    @FXML
    private ImageView imTakenOut;
    @FXML
    private Label lblMemberTakenOut;
    @FXML
    private ImageView imExit;
    @FXML
    private BorderPane bpMain;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Platform.runLater(new Runnable() {
            @Override
            public void run(){            
                
                // Getting the Member stage
                stageMember = (Stage) anchorPaneBackground.getScene().getWindow();                             
            }
        });
    }
    
    public void currentUser(String userid){
        currentUser = userid;
    }
    
    private void loadUI(String stage){
        Parent root = null;
        
        try{
            root = FXMLLoader.load(getClass().getResource("/stages/"+stage+".fxml"));
        }catch(IOException err){
            err.printStackTrace();
        }
        
        bpMain.setCenter(root);
    }

    @FXML
    private void lblAccountClicked(MouseEvent event) {
    }

    @FXML
    private void lblSettingsClicked(MouseEvent event) {
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
            stageMember.hide();
        }catch(IOException err){
            err.printStackTrace();
        }
    }

    @FXML
    private void hBoxBooksAvailableClicked(MouseEvent event) {
        loadUI("ListAvailableBooks");
    }

    @FXML
    private void hBoxBooksListClicked(MouseEvent event) {
        loadUI("ListBooks");
    }

    @FXML
    private void lblBooksRequestClicked(MouseEvent event) {
        System.out.println(currentUser);
    }

    @FXML
    private void lblMemberReturnDatesClicked(MouseEvent event) {
    }

    @FXML
    private void lblMemberTakenOutClicked(MouseEvent event) {
        loadUI("TakenOut");
    }
    
    
}
