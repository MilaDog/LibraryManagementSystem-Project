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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarymanagementsystemproject.Checks;
import librarymanagementsystemproject.Members;
import librarymanagementsystemproject.Postgres;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class SignInController implements Initializable {
    
    private static Checks check = new Checks();
    private static Postgres db = new Postgres();
    private static Members mem = new Members();
    
    private Stage stageSignIn = null;
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
                lblSignIn.requestFocus();                
                
                // Getting the SignIn  stage (window) so that it can be hidden when the suer wishes to create an account
                stageSignIn = (Stage) anchorPaneBackground.getScene().getWindow();
                
            }
        });
    }    

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
        // Reseting error labels
        lblSignInError.setText("");
        
        boolean loginFlag = true;
        String email = "";
        String password = pfPassword.getText();

        // Checking for password - Presence, valid
        if(password.isEmpty()){
            lblSignInError.setText("Enter in your password.");
            loginFlag = false;
        }        
        
        // Checking for email - Presence, valid
        if(!txfEmail.getText().isEmpty()){
            email = txfEmail.getText();
            
            if(!check.existingEmail(email)){// Checking if the member is registered
                lblSignInError.setText("You are not a registered member.");
                loginFlag = false;
            }
            
        }else{
            lblSignInError.setText("Enter in your email.");
            loginFlag = false;
        }        
        
        // Checking if everything is valid. If so, proceeds to next stage
        if(loginFlag){
            /*
                If all details were correct, proceeds to the default menu - MemberMenuGUI.
                If the logging in user is in the Staff Table, they proceed to the StaffMenuGUI
            */    
            
            // Checking that the inputed details correspond - password is correct for inputted email address
            if(!check.checkLogin(email, password)){
                lblSignInError.setText("Your email and password do not match.");
                loginFlag = false;
            }else{            
                String id = mem.getMember(email); 
                System.out.println(id);

                try{
                    FXMLLoader loader = null;
                    Parent root = null;
                    if(check.isStaffEmail(email)){

                        // Loading root of Staff. Getting a stage so that it can be viewed and the user can create their new accont
                        loader = new FXMLLoader(getClass().getResource("/stages/Staff.fxml"));
                        root = (Parent) loader.load();
                        
                        StaffController staffController = loader.getController();
                        
                        Scene staff = new Scene(root);
                        Stage stageStaff = new Stage();            
                        stageStaff.initStyle(StageStyle.UNDECORATED);
                        stageStaff.setScene(staff);
                        
                        staffController.currentUser(id);
                        
                        stageStaff.show();
                        stageSignIn.hide();
                        
                    }else{

                        // Loading root of Member. Getting a stage so that it can be viewed and the user can create their new accont
                        loader = new FXMLLoader(getClass().getResource("/stages/Member.fxml"));
                        root = (Parent) loader.load();
                        
                        MemberController memController = loader.getController();
                        
                        Scene member = new Scene(root);
                        Stage stageMember = new Stage();            
                        stageMember.initStyle(StageStyle.UNDECORATED);
                        stageMember.setScene(member);
                        
                        memController.currentUser(id);
                        
                        stageMember.show();
                        stageSignIn.hide();              
                    }// END if
                }catch(IOException err){
                    err.printStackTrace();
                }
                
            }// END if
        }// END if
    }

    @FXML
    private void cbPasswordViewClicked(ActionEvent event) {
        if(cbPasswordView.isSelected()){
            String password = new String(pfPassword.getText());
            txfPasswordView.setText(password);
            txfPasswordView.setVisible(true);
            pfPassword.setVisible(false);
        }else{
            String password = new String(pfPassword.getText());
            pfPassword.setText(password);
            pfPassword.setVisible(true);
            txfPasswordView.setVisible(false);            
        }// END passwordView
    }

    @FXML
    private void lblSignUpClicked(MouseEvent event) {                
        try{            
            // Loading root of SignUp. Getting a stage so that it can be viewed and the user can create their new accont
            Parent root = FXMLLoader.load(getClass().getResource("/stages/SignUp.fxml"));
            Scene signUp = new Scene(root);
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

    @FXML
    private void lblExitClicked(MouseEvent event) {
        System.exit(0);
    }

    
}
