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
 * @author Daniel Ryan Sergeant
 */
public class SignUpController implements Initializable {

    // Instantiating necessary Objects
    private Checks check = new Checks(); 
    private Postgres db = new Postgres();
    private Algorithms algor = new Algorithms();
    @FXML
    private Label lblFirstNameError;
    @FXML
    private Label lblSurnameError;
    @FXML
    private Label lblPhoneCellNumberError;
    @FXML
    private Label lblEmailError;
    @FXML
    private Label lblDOBError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblPasswordCheckError;
    public void testMethod() {
        
    }
    private LocalDate dob = null;    
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
                // Requesting focus so that when the stage is loaded, the cursor is not automatically put into a text field
                lblSignUp.requestFocus();                
                
                // Getting the SignIn  stage (window) so that it can be hidden when the suer wishes to create an account
                stageSignUp = (Stage) anchorPaneBackground.getScene().getWindow();
                
            }
        });
    }   

    @FXML
    private void btnSignUpClicked(ActionEvent event) {
        // Resetting all error labels and styles      
        lblFirstNameError.setText("");
        lblSurnameError.setText("");
        lblEmailError.setText("");
        lblPhoneCellNumberError.setText("");
        lblDOBError.setText("");
        lblPasswordError.setText("");
        lblPasswordCheckError.setText("");
        lblAccountExists.setText("");
        
        txfFirstName.getStyleClass().removeAll("error");
        txfSurname.getStyleClass().removeAll("error");
        txfPhoneCellNumber.getStyleClass().removeAll("error");
        txfEmail.getStyleClass().removeAll("error");
        dpDOB.getStyleClass().removeAll("errorDP");
        pfPassword.getStyleClass().removeAll("error");
        pfPasswordCheck.getStyleClass().removeAll("error");
        txfPasswordView.getStyleClass().removeAll("error");
        txfPasswordCheckView.getStyleClass().removeAll("error");

        
        // Getting all inputed information
        boolean accountFlag = true;
        String firstName = "";
        String surname = "";
        String email = "";
        String phone = "";        
        String formatDob = "";
        
        // Getting passwords entered
        String password1 = pfPassword.getText();
        String password2 = pfPasswordCheck.getText();
        
        // Option to display inputted password - see what the user has typed in
        if(cbPasswordView.isSelected()){
            pfPassword.setText(txfPasswordView.getText());
            pfPasswordCheck.setText(txfPasswordCheckView.getText());
        }// END if - check if user has left 'View password' checked
        
        
        // Checking FirstName - Presence, Valid, Logic
        if(!txfFirstName.getText().isEmpty()){
            firstName = txfFirstName.getText();
            
            if(!check.checkNames(firstName)){ // if name contains invalid characters
                lblFirstNameError.setText("Conatins invalid characters.");
                txfFirstName.getStyleClass().add("error");
                accountFlag = false;
            }
            
        }else{ // field left empty
            lblFirstNameError.setText("Please provide.");
            txfFirstName.getStyleClass().add("error");
            accountFlag = false;
        }
        
        
        // Checking Surname - Presence, Valid, Logic
        if(!txfSurname.getText().isEmpty()){
            surname = txfSurname.getText();
            
            if(!check.checkNames(surname)){ // if surname contains invalid characters
                lblSurnameError.setText("Conatins invalid characters.");
                txfSurname.getStyleClass().add("error");
                accountFlag = false;
            }
            
        }else{ // field left empty
            lblSurnameError.setText("Please provide.");
            txfSurname.getStyleClass().add("error");
            accountFlag = false;
        }       
        
        
        // Checking Email - Presence, Valid, Logic, Format
        if(!txfEmail.getText().isEmpty()){
            email = txfEmail.getText();
                        
            if(!check.existingEmail(email)){ // if email exists
                if(check.emailContainsAT(email)){ // if email contains '@' character
                    if(!check.checkEmail(email)){ // if email contains invalid characters / missing domain or local part
                        lblEmailError.setText("Conatins invalid characters / missing domain or local part.");
                        txfEmail.getStyleClass().add("error");
                        accountFlag = false;
                    }
                }else{
                    lblEmailError.setText("Does not contain the '@' character.");
                    txfEmail.getStyleClass().add("error");
                    accountFlag = false;
                }
            }else{ // if email exists
                lblAccountExists.setText("Account with such email already exists.");
                accountFlag = false;
            }
            
        }else{ // field is empty
            lblEmailError.setText("Please provide.");
            txfEmail.getStyleClass().add("error");
            accountFlag = false;
        }

        
        // Checking Phone number - Presence
        if(!txfPhoneCellNumber.getText().isEmpty()){
            phone = txfPhoneCellNumber.getText();  
             
            // checking phone length and valid characters
            if(!check.checkPhoneCellLength(phone)){
                lblPhoneCellNumberError.setText("Length does not equal 10.");
                txfPhoneCellNumber.getStyleClass().add("error");
                accountFlag = false;
            }else if(!check.checkPhoneCellValidCharactersOrZero(phone)){
                lblPhoneCellNumberError.setText("Contains non-numeric characters / does not start with 0");
                txfPhoneCellNumber.getStyleClass().add("error");
                accountFlag = false;
            }
            
        }else{ // field is empty
            lblPhoneCellNumberError.setText("Please provide.");
            txfPhoneCellNumber.getStyleClass().add("error");
            accountFlag = false;
        }
        
        
        // Checking if Date of Birth was selected - Presence
        try{
            dob = dpDOB.getValue();
            formatDob = Utils.formatDate(dob);
        }catch(NullPointerException err){
            lblDOBError.setText("Please provide.");
            dpDOB.getStyleClass().add("errorDP");
            accountFlag = false;
        }
        
        
        // Checking if password was entered and correct - Presence, Valid, Logic
        if(password1.isEmpty() && password2.isEmpty()){
            lblPasswordError.setText("Please provide.");
            pfPassword.getStyleClass().add("error");
            txfPasswordView.getStyleClass().add("error");
            accountFlag = false;
        }        
        else if(password2.isEmpty() && !password1.isEmpty()){
            lblPasswordCheckError.setText("Please re-enter password.");
            pfPasswordCheck.getStyleClass().add("error");
            txfPasswordCheckView.getStyleClass().add("error");
            accountFlag = false;
        }        
        else if(!password1.isEmpty() && password2.isEmpty()){
            lblPasswordCheckError.setText("Please provide.");
            pfPasswordCheck.getStyleClass().add("error");
            txfPasswordCheckView.getStyleClass().add("error");
            accountFlag = false;
        }else{
            if(!check.checkPassword(password1, password2)){
                lblPasswordError.setText("Does not match");
                pfPassword.getStyleClass().add("error");
                txfPasswordView.getStyleClass().add("error");
                lblPasswordCheckError.setText("Does not match.");
                pfPasswordCheck.getStyleClass().add("error");
                txfPasswordCheckView.getStyleClass().add("error");
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
            
            // Adding the newly registered user's details to the database
            db.update(userQuery);
            db.update(loginQuery);
            
            
            // Going to the default menu - MemberMenuGUI
            try{
                    // Loading root of Member. Getting a stage so that it can be viewed and the user can create their new accont
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/stages/Member.fxml"));
                    Parent root = (Parent) loader.load();
                        
                    // Passing currentUser to MemberController so that it can be used
                    MemberController memberController = loader.getController();
                    memberController.currentUser(userID);

                    // Setting up the stage
                    Scene member = new Scene(root);
                    Stage stageMember = new Stage();            
                    stageMember.initStyle(StageStyle.UNDECORATED);
                    stageMember.setScene(member);

                    stageMember.show();
                    stageSignUp.hide(); 
            }catch(IOException err){
                err.printStackTrace();
            }// END try-catch
            
        }// END signUp check
    }
    
    // When clicked, closes the whole program
    @FXML
    private void lblExitClicked(MouseEvent event) {
        System.exit(0);
    }


    // When the checkbox is clicked, displays what was entered in the password field. When unclicked, copies what the text in the 
    // visible text field is to the password field.
    @FXML
    private void cbPasswordViewClicked(ActionEvent event) {
        if(cbPasswordView.isSelected()){
            String password = pfPassword.getText();
            String passwordCheck = pfPasswordCheck.getText();
            
            // Normal password
            txfPasswordView.setText(password);
            txfPasswordView.setVisible(true);
            pfPassword.setVisible(false);
            
            // Password re-enter
            txfPasswordCheckView.setText(passwordCheck);
            txfPasswordCheckView.setVisible(true);
            pfPasswordCheck.setVisible(false);
            
        }else{
            String password = txfPasswordView.getText();
            String passwordCheck = txfPasswordCheckView.getText();
            
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

    // When clicked, loads the SignIn stage
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
