package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarymanagementsystemproject.Checks;
import librarymanagementsystemproject.Members;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class SignInController implements Initializable {
    
    // Instantiating necessary Objects
    private Checks check = new Checks();
    private Members mem = new Members();
    
    private Stage stageSignIn = null;
    
    // Variables used to make the mvoing of the stage smooth
    private double xMouse;
    private double yMouse;

    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneSignIn;
    @FXML
    private TextField txfEmail;
    @FXML
    private ImageView imageEmail;
    @FXML
    private ImageView imagePassword;
    @FXML
    private Label lblSignIn;
    @FXML
    private Button btnSignIn;
    @FXML
    private TextField txfPasswordView;
    @FXML
    private Label lblSignInError;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private ImageView lblExit;
    @FXML
    private CheckBox cbPasswordView;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblSignUp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                // Requesting focus so that when the stage is loaded, is does not automatically move the cursor into the email field
                lblSignIn.requestFocus();                
                
                // Getting the SignIn  stage (window) so that it can be hidden when the suer wishes to create an account
                stageSignIn = (Stage) anchorPaneBackground.getScene().getWindow();
                
            }
        });
    }    

    // Below used for the movement of the stage
    @FXML
    private void anchorPaneBackgroundOnMouseDragged(MouseEvent event) {
        stageSignIn.setX(event.getScreenX() - xMouse);
        stageSignIn.setY(event.getScreenY() - yMouse);
    }

    @FXML
    private void anchorPaneBackgroundOnMousePressed(MouseEvent event) {
        xMouse = event.getSceneX();
        yMouse = event.getSceneY();
    }
    
    @FXML
    private void btnSignInClicked(ActionEvent event) {       
        
        // Reseting error labels and styles
        lblSignInError.setText("");
        
        txfEmail.getStyleClass().removeAll("error");
        pfPassword.getStyleClass().removeAll("errror");        
        txfPasswordView.getStyleClass().removeAll("error");
        
        boolean loginFlag = true;
        String email = "";        
        String password = pfPassword.getText();
        
        if(cbPasswordView.isSelected()){
            pfPassword.setText(txfPasswordView.getText());
        }// END if - check if user left 'View password' on. Gets that input

        // Checking for password and email - Presence, valid
        if(password.isEmpty() && txfEmail.getText().isEmpty()){
            lblSignInError.setText("Enter in your email address and password.");
            
            txfEmail.getStyleClass().add("error");
            pfPassword.getStyleClass().add("error");
            txfPasswordView.getStyleClass().add("error");
            
            loginFlag = false;
        }else if(password.isEmpty()){
            lblSignInError.setText("Enter in your password.");
            pfPassword.getStyleClass().add("error");
            txfPasswordView.getStyleClass().add("error");            
            loginFlag = false;
        }   
    
        
        // Checking if everything is valid. If so, proceeds to next stage
        if(loginFlag){
            /*
                If all details were correct, proceeds to the default menu - MemberMenuGUI.
                If the logging in user is in the Staff Table, they proceed to the StaffMenuGUI
            */    
            
            email = txfEmail.getText().replace(" ", "");
            
            // Checking for email existence - Presence
            if(!check.existingEmail(email)){// Checking if the member is registered
                    lblSignInError.setText("You are not a registered member.");
            }else{  
                // Checking that the inputed details correspond - password is correct for inputted email address
                if(!check.checkLogin(email, password)){
                    lblSignInError.setText("Your email and password do not match.");
                    loginFlag = false;
                }else{       
                    // Getting the member object
                    RegisteredUsers user = mem.getMemberByEmail(email); 
                    String userID = user.getUserID();

                    try{
                        FXMLLoader loader = null;
                        Parent root = null;
                        if(check.isStaffEmail(email)){

                            // Loading root of Staff. Getting a stage so that it can be viewed and the user can create their new accont
                            loader = new FXMLLoader(getClass().getResource("/stages/Staff.fxml"));
                            root = (Parent) loader.load();

                            // Passing currentUser to StaffController so that it can be used
                            StaffController staffController = loader.getController();
                            staffController.currentUser(userID);

                            // Setting up the stage
                            Scene staff = new Scene(root);
                            Stage stageStaff = new Stage();            
                            stageStaff.initStyle(StageStyle.UNDECORATED);
                            stageStaff.setScene(staff);

                            stageStaff.show();
                            stageSignIn.hide();

                        }else{


                            // Loading root of Member. Getting a stage so that it can be viewed and the user can create their new accont
                            loader = new FXMLLoader(getClass().getResource("/stages/Member.fxml"));                        
                            root = (Parent) loader.load();

                            // Passing currentUser to MemberController so that it can be used
                            MemberController memberController = loader.getController();
                            memberController.currentUser(userID);

                            // Setting up the stage
                            Scene member = new Scene(root);
                            Stage stageMember = new Stage();            
                            stageMember.initStyle(StageStyle.UNDECORATED);
                            stageMember.setScene(member);

                            stageMember.show();
                            stageSignIn.hide();              
                        }// END if
                    }catch(IOException err){
                        err.printStackTrace();
                    }
                }
                }// END if
        }// END if
    }

    // When the checkbox is clicked, displays what was entered in the password field. When unclicked, copies what the text in the 
    // visible text field is to the password field.
    @FXML
    private void cbPasswordViewClicked(ActionEvent event) {
        if(cbPasswordView.isSelected()){
            String password = pfPassword.getText();
            txfPasswordView.setText(password);
            txfPasswordView.setVisible(true);
            pfPassword.setVisible(false);
        }else{
            String password = txfPasswordView.getText();
            pfPassword.setText(password);
            pfPassword.setVisible(true);
            txfPasswordView.setVisible(false);            
        }// END passwordView
    }

    // When clicked, loads the SignUp stage
    @FXML
    private void lblSignUpClicked(MouseEvent event) {                
        try{            
            // Loading root of SignUp. Getting a stage so that it can be viewed and the user can create their new accont
            Parent root = FXMLLoader.load(getClass().getResource("/stages/SignUp.fxml"));
            Scene signUp = new Scene(root);
            
            // loading scene custom stylesheet for errors
            signUp.getStylesheets().add(getClass().getResource("/stylesheets/errors.css").toExternalForm());
            
            Stage stageSignUp = new Stage();            
            stageSignUp.initStyle(StageStyle.UNDECORATED);
            stageSignUp.setScene(signUp);
            stageSignUp.show();
            stageSignUp.centerOnScreen();
            stageSignIn.hide();            
            
        }catch(IOException err){
            err.printStackTrace();
        }// END try-catch
    }

    // When clicked, closes the whole program
    @FXML
    private void lblExitClicked(MouseEvent event) {
        System.exit(0);
    }

    
}
