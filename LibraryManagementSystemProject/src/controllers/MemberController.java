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
 * @author Daniel Ryan Sergeant
 */
public class MemberController implements Initializable {

    // Initializing necessary variables
    private String currentUser = "";
    
    // Ability to mouse the stage (window) - move smoothly
    private double xMouse;
    private double yMouse;
    
    // Initializing necessary Objects
    private Members mem = new Members();
    private Stage stageMember = null;
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneSideMenu;
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
    @FXML
    private ImageView lblSettingsOpen;
    @FXML
    private ImageView lblSettingsClose;
    
    
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
                
                // Getting the current member's object so that their name can be displayed on the stage
                RegisteredUsers user = mem.getMemberByUserID(currentUser);
                String userName = user.getFirstName();
                lblMemberName.setText(userName);
                
            }
        });
    }
    
    public void currentUser(String userid){
        currentUser = userid;
    }
    
    /*
        loadUI - @param takes the stage name, as a String, and loads that stage.
                 @param goes through checks and if the check is true, it passes the current logged in member's UserID to that stage
                        so that it can be used in the respective stages
    */
    private void loadUI(String stage){
        Parent root = null;
        FXMLLoader loader = null;
        
        try{
            loader = new FXMLLoader(getClass().getResource("/stages/"+stage+".fxml"));
            root = (Parent) loader.load();
            
            // Checks - see which stage needs the current member's UserID
            if(stage.equalsIgnoreCase("takenout")){
                TakenOutController takenOutController = loader.getController();
                takenOutController.currentUser(currentUser);
            }else if(stage.equalsIgnoreCase("returndates")){
                ReturnDatesController returnDatesController = loader.getController();
                returnDatesController.currentUser(currentUser);
            }else if(stage.equalsIgnoreCase("requestbook")){
                RequestBookController requestBookController = loader.getController();
                requestBookController.currentUser(currentUser);
            }
            
        }catch(IOException err){
            err.printStackTrace();
        }
        
        // Displaying the stage on the main stage, Member
        bpMain.setCenter(root);
    }

    @FXML
    private void lblExitClicked(MouseEvent event) {
        // Closing Member stage and going to the SignIn stage if the user closes the window
        try{
            // Loading root of SignIn. Getting the stage so that it can be displayed when the current user closes the stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stages/SignIn.fxml"));
            Parent root = (Parent) loader.load();

            Scene signin = new Scene(root);
            Stage stageSignIn = new Stage();            
            stageSignIn.initStyle(StageStyle.UNDECORATED);
            stageSignIn.setScene(signin);

            // Displaying the SignIn stage and Hiding the Member stage
            stageSignIn.show();
            stageMember.hide();
        }catch(IOException err){
            err.printStackTrace();
        }
    }

    /*
        Below are all of the events for each side menu hBox (field). 
        Loads respective stage
    */
    
    @FXML
    private void hBoxBooksAvailableClicked(MouseEvent event) {
        loadUI("ListAvailableBooks");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxBooksListClicked(MouseEvent event) {
        loadUI("ListBooks");
        bpSettings.setVisible(false);
    }


    @FXML
    private void hBoxBooksRequestClicked(MouseEvent event) {
        loadUI("RequestBook");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxMemberReturnDatesClicked(MouseEvent event) {
        loadUI("ReturnDates");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxMemberTakenOutClicked(MouseEvent event) {
        loadUI("TakenOut");
        bpSettings.setVisible(false);
    }

    // Ability to mouse the stage (window) - move smoothly
    @FXML
    private void anchorPaneBackgroundOnMouseDragged(MouseEvent event) {
        stageMember.setX(event.getScreenX() - xMouse);
        stageMember.setY(event.getScreenY() - yMouse);
    }
    
    // Ability to mouse the stage (window) - move smoothly
    @FXML
    private void anchorPaneBackgroundOnMousePressed(MouseEvent event) {
        xMouse = event.getSceneX();
        yMouse = event.getSceneY();
    }

    /*
        Below is for the Setting Icon. When clicked, displays the settings menu, where the user can then interact with what is in the menu.
        When the icon is clicked again, it is hidden.
        Also, if the user opens the Settings menu, and tkhen proceeds to open another stage, the Settings menu is hidden
    */
    
    @FXML
    private void lblSettingsOpenClicked(MouseEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        
        try{
            loader = new FXMLLoader(getClass().getResource("/stages/Settings.fxml"));
            root = (Parent) loader.load();
            
        }catch(IOException err){
            err.printStackTrace();
        }
        
        // The are two Settings icons, both with different events. Hides the current setting icon and displays the other icon
        bpSettings.setCenter(root);
        bpSettings.setVisible(true);
        bpSettings.toFront();
        lblSettingsClose.setVisible(true);
        lblSettingsOpen.setVisible(false);
    }

    @FXML
    private void lblSettingsClosedClicked(MouseEvent event) {
        bpSettings.setVisible(false);
        lblSettingsOpen.setVisible(true);
        lblSettingsClose.setVisible(false);
    }
    
}
