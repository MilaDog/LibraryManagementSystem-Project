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
import librarymanagementsystemproject.Members;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class MemberController implements Initializable {

    private String currentUser = "";
    
    private Members mem = new Members();
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
    @FXML
    private BorderPane bpSettings;
    
    
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
                
                RegisteredUsers user = mem.getMemberByUserID(currentUser);
                String userName = user.getFirstName();
                lblMemberName.setText(userName);
                
            }
        });
    }
    
    public void currentUser(String userid){
        currentUser = userid;
    }
    
    private void loadUI(String stage){
        Parent root = null;
        FXMLLoader loader = null;
        
        try{
            loader = new FXMLLoader(getClass().getResource("/stages/"+stage+".fxml"));
            root = (Parent) loader.load();
            
            if(stage.equalsIgnoreCase("takenout")){
                TakenOutController takenOutController = loader.getController();
                takenOutController.currentUser(currentUser);
            }else if(stage.equalsIgnoreCase("returndates")){
                ReturnDatesController returnDatesController = loader.getController();
                returnDatesController.currentUser(currentUser);
            }else if(stage.equalsIgnoreCase("requestbook")){
                RequestBookController requestBookController = loader.getController();
                requestBookController.currentUser(currentUser);
            }else if(stage.equalsIgnoreCase("account")){
                AccountController accountController = loader.getController();
                accountController.currentUser(currentUser);
            }
            
        }catch(IOException err){
            err.printStackTrace();
        }
        
        bpMain.setCenter(root);
    }

    @FXML
    private void lblAccountClicked(MouseEvent event) {
        loadUI("Account");
    }

    @FXML
    private void lblSettingsClicked(MouseEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        
        try{
            loader = new FXMLLoader(getClass().getResource("/stages/Settings.fxml"));
            root = (Parent) loader.load();
            
        }catch(IOException err){
            err.printStackTrace();
        }
        
        bpSettings.setCenter(root);
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
        loadUI("RequestBook");
    }

    @FXML
    private void lblMemberReturnDatesClicked(MouseEvent event) {
        loadUI("ReturnDates");
    }

    @FXML
    private void lblMemberTakenOutClicked(MouseEvent event) {
        loadUI("TakenOut");
    }
    
    
}
