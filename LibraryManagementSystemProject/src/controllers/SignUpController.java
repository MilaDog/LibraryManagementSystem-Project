/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarymanagementsystemproject.Algorithms;
import librarymanagementsystemproject.Checks;
import librarymanagementsystemproject.Postgres;
import librarymanagementsystemproject.Utils;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class SignUpController implements Initializable {

    private static Checks check = new Checks(); 
    private static Postgres db = new Postgres();
    private static Algorithms algor = new Algorithms();
    private static LocalDate dob = null;
    
    private Stage stageSignUp = null;
    private Parent root = null;
    
    
    // Ability to mouse the stage (window) - move smoothly
    private double xMouse;
    private double yMouse;
    
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneSignUp;
    @FXML
    private Label lblSignUp;
    @FXML
    private Button btnSignUp;
    @FXML
    private TextField txfEmail;
    @FXML
    private TextField txfSurname;
    @FXML
    private TextField txfFirstName;
    @FXML
    private TextField txfPhoneCellNumber;
    @FXML
    private TextField txfPasswordView;
    @FXML
    private TextField txfPasswordCheckView;
    @FXML
    private DatePicker dpDOB;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfPasswordCheck;
    @FXML
    private CheckBox cbPasswordView;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblPhoneCellNumber;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblDOB;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblPasswordCheck;
    @FXML
    private Label lblWelcome;
    @FXML
    private ImageView lblExit;
    @FXML
    private Label lblAccountExists;
    @FXML
    private Label lblSignIn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                lblSignUp.requestFocus();                
                
                // Getting the SignIn  stage (window) so that it can be hidden when the suer wishes to create an account
                stageSignUp = (Stage) anchorPaneBackground.getScene().getWindow();
                
            }
        });
    }   

    @FXML
    private void btnSignUpClicked(ActionEvent event) {
        // Resetting all error labels        
        lblFirstName.setText("First Name");
        lblSurname.setText("Surname");
        lblEmail.setText("Email Address");
        lblPhoneCellNumber.setText("Phone/Cell Number");
        lblDOB.setText("Date of Birth");
        lblPassword.setText("Password");
        lblPasswordCheck.setText("Re-enter Password");
        lblAccountExists.setText("");
        

        
        // Getting all inputed information
        boolean accountFlag = true;
        String firstName = "";
        String surname = "";
        String email = "";
        String phone = "";        
        String formatDob = "";
        
        String password1 = pfPassword.getText();
        String password2 = pfPasswordCheck.getText();
        
        
        // Checking FirstName - Presence, Valid, Logic
        if(!txfFirstName.getText().isEmpty()){
            firstName = txfFirstName.getText();
            
            if(!check.checkNames(firstName)){
                lblFirstName.setText("First Name - Not Valid.");
                accountFlag = false;
            }
            
        }else{
            lblFirstName.setText("First Name - Please provide.");
            accountFlag = false;
        }
        
        
        // Checking Surname - Presence, Valid, Logic
        if(!txfSurname.getText().isEmpty()){
            surname = txfSurname.getText();
            
            if(!check.checkNames(surname)){
                lblSurname.setText("Surname - Not Valid.");
                accountFlag = false;
            }
            
        }else{
            lblSurname.setText("Surname - Please provide.");
            accountFlag = false;
        }       
        
        
        // Checking Email - Presence, Valid, Logic, Format
        if(!txfEmail.getText().isEmpty()){
            email = txfEmail.getText();
                        
            if(!check.existingEmail(email)){
                if(!check.checkEmail(email)){
                    lblEmail.setText("Email - Invalid.");
                    accountFlag = false;
                }
            }else{
                lblAccountExists.setText("Account with such email already exists.");
                accountFlag = false;
            }
            
        }else{
            lblEmail.setText("Email - Please provide.");
            accountFlag = false;
        }

        
        // Checking Phone number - Presence
        if(!txfPhoneCellNumber.getText().isEmpty()){
            phone = txfPhoneCellNumber.getText();  
            
            if(!check.checkPhoneCell(phone)){
                lblPhoneCellNumber.setText("Phone/Cell Number - Invalid.");
                accountFlag = false;
            }
            
        }else{
            lblPhoneCellNumber.setText("Phone/Cell Number - Please provide.");
            accountFlag = false;
        }
        
        
        // Checking if Date of Birth was selected - Presence
        try{
            dob = dpDOB.getValue();
            formatDob = Utils.formatDate(dob);
        }catch(NullPointerException err){
            lblDOB.setText("Date of Birth - Please provide.");
            accountFlag = false;
        }
        
        
        // Checking if password was entered and correct - Presence, Valid, Logic
        if(password1.isEmpty() && password2.isEmpty()){
            lblPassword.setText("Password - Please provide.");
            accountFlag = false;
        }        
        else if(password2.isEmpty() && !password1.isEmpty()){
            lblPasswordCheck.setText("Password - Please re-enter password.");
            accountFlag = false;
        }        
        else if(!password1.isEmpty() && password2.isEmpty()){
            lblPasswordCheck.setText("Re-enter Password - Please provide.");
            accountFlag = false;
        }else{
            if(!check.checkPassword(password1, password2)){
                lblPassword.setText("Password - Do not match");
                lblPasswordCheck.setText("Re-enter Password - Do not match.");
                accountFlag = false;
            }
        }
        
        
        // If all is correct, add the new member to the database and save their login details
        if(accountFlag){
            // Generating the unique UserID
            String userID = algor.generateUserID(firstName, surname, formatDob);
            
            // Querying and storing data in the database - registered_users and login_details
            String userQuery = String.format("INSERT INTO registered_users(userid, first_name, surname, dob, phone, email) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", userID, firstName, surname, dob, phone, email);
            String loginQuery = String.format("INSERT INTO login_details(userid, password) VALUES ('%s', '%s')", userID, password1);
            
            db.update(userQuery);
            db.update(loginQuery);
            
            
            // Going to the default menu - MemberMenuGUI
            try{
                    // Loading root of Member. Getting a stage so that it can be viewed and the user can create their new accont
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/stages/Member.fxml"));
                    Parent root = (Parent) loader.load();

                    MemberController memController = loader.getController();

                    Scene member = new Scene(root);
                    Stage stageMember = new Stage();            
                    stageMember.initStyle(StageStyle.UNDECORATED);
                    stageMember.setScene(member);

                    memController.currentUser(userID);

                    stageMember.show();
                    stageSignUp.hide(); 
            }catch(IOException err){
                err.printStackTrace();
            }// END try-catch
            
        }// END signUp check
    }
    
    @FXML
    private void lblExitClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void cbPasswordViewClicked(ActionEvent event) {
        if(cbPasswordView.isSelected()){
            String password = new String(pfPassword.getText());
            String passwordCheck = new String(pfPasswordCheck.getText());
            
            // Normal password
            txfPasswordView.setText(password);
            txfPasswordView.setVisible(true);
            pfPassword.setVisible(false);
            
            // Password re-enter
            txfPasswordCheckView.setText(passwordCheck);
            txfPasswordCheckView.setVisible(true);
            pfPasswordCheck.setVisible(false);
            
        }else{
            String password = new String(pfPassword.getText());
            String passwordCheck = new String(pfPasswordCheck.getText());
            
            // Normal password
            pfPassword.setText(password);
            pfPassword.setVisible(true);
            txfPasswordView.setVisible(false);  
            
            // Password re-enter
            pfPasswordCheck.setText(passwordCheck);
            pfPasswordCheck.setVisible(true);
            txfPasswordCheckView.setVisible(false);          
        }// END passwordView
    }
    

    // Ability to mouse the stage (window) - move smoothly
    @FXML
    private void anchorPaneBackgroundOnMouseDragged(MouseEvent event) {
        stageSignUp.setX(event.getScreenX() - xMouse);
        stageSignUp.setY(event.getScreenY() - yMouse);
    }
    
    // Ability to mouse the stage (window) - move smoothly
    @FXML
    private void anchorPaneBackgroundOnMousePressed(MouseEvent event) {
        xMouse = event.getSceneX();
        yMouse = event.getSceneY();
    }

    @FXML
    private void lblSignInClicked(MouseEvent event) {               
        try{            
            // Loading root of SignIn. Getting a stage so that it can be viewed and the user can create their new accont
            Parent root = FXMLLoader.load(getClass().getResource("/stages/SignIn.fxml"));
            Scene signIn = new Scene(root);
            Stage stageSignIn = new Stage();            
            stageSignIn.initStyle(StageStyle.UNDECORATED);
            stageSignIn.setScene(signIn);
            stageSignIn.show();
            stageSignIn.centerOnScreen();
            stageSignUp.hide();
            
            
        }catch(IOException err){
            err.printStackTrace();
        }// END try-catch
    }


 
}
