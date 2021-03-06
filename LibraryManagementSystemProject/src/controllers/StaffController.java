
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
public class StaffController implements Initializable {

    private Stage stageStaff = null;  
    
    // Instantiating necessary objects
    private Members mem = new Members();
    
    // Initializing necessary variables
    private String currentUser = "";
        
    // Ability to mouse the stage (window) - move smoothly
    private double xMouse;
    private double yMouse;
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneSideMenu;
    @FXML
    private BorderPane bpSettings;
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
    private ImageView imReturnDates;
    @FXML
    private ImageView imTakenOut;
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
    @FXML
    private ImageView lblSettingsOpen;
    @FXML
    private ImageView lblSettingsClose;
    @FXML
    private HBox hBoxReturnDates;
    @FXML
    private Label lblReturnDates;
    @FXML
    private HBox hBoxTakenOut;
    @FXML
    private Label lblTakenOut;
    @FXML
    private HBox hBoxFixedBook;
    @FXML
    private ImageView imFixedBooks;
    @FXML
    private Label lblFixedBooks;
    
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
            }else if(stage.equalsIgnoreCase("managestaffremove")){
                ManageStaffRemoveController staffRemoveController = loader.getController();
                staffRemoveController.currentUser(currentUser);
            }else if(stage.equalsIgnoreCase("fixbook")){
                FixBookController fixBookController = loader.getController();
                fixBookController.currentUser(currentUser);
            }
            
        }catch(IOException err){
            err.printStackTrace();
        }
        
        // Displaying the stage on the main stage, Staff
        bpMain.setCenter(root);
    }
    
    // Ability to mouse the stage (window) - move smoothly
    @FXML
    private void anchorPaneBackgroundOnMouseDragged(MouseEvent event) {
        stageStaff.setX(event.getScreenX() - xMouse);
        stageStaff.setY(event.getScreenY() - yMouse);
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
             
        bpSettings.setCenter(root);
        bpSettings.setVisible(true);
        bpSettings.toFront();
        lblSettingsClose.setVisible(true);
        lblSettingsOpen.setVisible(false);
    }

    @FXML
    private void lblSettingsCloseClicked(MouseEvent event) {
        bpSettings.setVisible(false);
        lblSettingsOpen.setVisible(true);
        lblSettingsClose.setVisible(false);
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
    private void hBoxReturnDatesClicked(MouseEvent event) {
        loadUI("ReturnDates");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxTakenOutClicked(MouseEvent event) {
        loadUI("TakenOut");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxIssueBooksClicked(MouseEvent event) {
        loadUI("BookIssue");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxReturnBookClicked(MouseEvent event) {
        loadUI("BookReturn");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxFixBookClicked(MouseEvent event) {
        loadUI("FixBook");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxFixedBookClicked(MouseEvent event) {
        loadUI("FixedBooks");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxOutstandingBooksClicked(MouseEvent event) {
        loadUI("BooksOutstanding");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxViewRequestedBooksClicked(MouseEvent event) {
        loadUI("RequestedBooks");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxStaffAddClicked(MouseEvent event) {
        loadUI("ManageStaffAdd");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxStaffRemoveClicked(MouseEvent event) {
        loadUI("ManageStaffRemove");
        bpSettings.setVisible(false);
    }

    @FXML
    private void hBoxStaffViewClicked(MouseEvent event) {
        loadUI("ManageStaffView");
        bpSettings.setVisible(false);
    }

    @FXML
    private void lblExitClicked(MouseEvent event) {
        bpSettings.setVisible(false);
        
        // Closing Member stage and going to the SignIn stage if the user closes the window
        try{
            // Loading root of Member. Getting a stage so that it can be viewed and the user can create their new accont
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stages/SignIn.fxml"));
            Parent root = (Parent) loader.load();

            Scene signin = new Scene(root);
            Stage stageSignIn = new Stage();            
            stageSignIn.initStyle(StageStyle.UNDECORATED);
            stageSignIn.setScene(signin);

            // Displaying the SignIn stage and Hiding the Staff stage
            stageSignIn.show();
            stageStaff.hide();
        }catch(IOException err){
            err.printStackTrace();
        }
    }
}
