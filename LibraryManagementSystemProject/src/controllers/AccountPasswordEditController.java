/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import librarymanagementsystemproject.Account;
import librarymanagementsystemproject.Checks;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class AccountPasswordEditController implements Initializable {

    private Checks check = new Checks();
    private Account account = new Account();
    private String currentUser = "";
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private Label lblCurrentPassword;
    @FXML
    private Label lblNewPassword;
    @FXML
    private Label lblNewPasswordRetype;
    @FXML
    private TextField txfCurrentPassword;
    @FXML
    private TextField txfNewPassword;
    @FXML
    private TextField txfNewPasswordRetype;
    @FXML
    private PasswordField pfCurrentPassword;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private PasswordField pfNewPasswordRetype;
    @FXML
    private CheckBox cbViewPassword;
    @FXML
    private CheckBox cbViewPasswordNew;
    @FXML
    private CheckBox cbViewPasswordNewRetype;
    @FXML
    private Button btnPasswordUpdate;
    @FXML
    private Label lblCurrentPasswordError;
    @FXML
    private Label lblNewPasswordError;
    @FXML
    private Label lblNewPasswordRetypeError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void currentUser(String userid){
        currentUser = userid;
    }

    @FXML
    private void cbViewPasswordClicked(MouseEvent event) {
        if(cbViewPassword.isSelected()){
            String password = pfCurrentPassword.getText();
            txfCurrentPassword.setText(password);
            txfCurrentPassword.setVisible(true);
            pfCurrentPassword.setVisible(false);
        }else{
            String password = txfCurrentPassword.getText();
            pfCurrentPassword.setText(password);
            pfCurrentPassword.setVisible(true);
            txfCurrentPassword.setVisible(false);  
        }
    }

    @FXML
    private void cbViewPasswordNewClicked(MouseEvent event) {
        if(cbViewPasswordNew.isSelected()){
            String password = pfNewPassword.getText();
            txfNewPassword.setText(password);
            txfNewPassword.setVisible(true);
            pfNewPassword.setVisible(false);
        }else{
            String password = txfNewPassword.getText();
            pfNewPassword.setText(password);
            pfNewPassword.setVisible(true);
            txfNewPassword.setVisible(false);  
        }
    }

    @FXML
    private void cbViewPasswordNewRetypeClicked(MouseEvent event) {
        if(cbViewPasswordNewRetype.isSelected()){
            String password = pfNewPasswordRetype.getText();
            txfNewPasswordRetype.setText(password);
            txfNewPasswordRetype.setVisible(true);
            pfNewPasswordRetype.setVisible(false);
        }else{
            String password = txfNewPasswordRetype.getText();
            pfNewPasswordRetype.setText(password);
            pfNewPasswordRetype.setVisible(true);
            txfNewPasswordRetype.setVisible(false);  
        }
    }

    @FXML
    private void btnPasswordUpdateClicked(MouseEvent event) {
        
        boolean flag = true;
        
        // Resetting error labels
        lblCurrentPasswordError.setText("");
        lblNewPasswordError.setText("");
        lblNewPasswordRetypeError.setText("");
        
        if(cbViewPassword.isSelected()){
            pfCurrentPassword.setText(txfCurrentPassword.getText());
        }else if(cbViewPasswordNew.isSelected()){
            pfNewPassword.setText(txfNewPassword.getText());
        }else if(cbViewPasswordNewRetype.isSelected()){
            pfNewPasswordRetype.setText(txfNewPasswordRetype.getText());
        }  
        
        // Checking that the current password is correct
        if(pfCurrentPassword.getText().isEmpty()){
            lblCurrentPasswordError.setText("Password - Please provide");
            flag = false;
        }else if(!check.checkExistingPassword(currentUser, pfCurrentPassword.getText())){
            lblCurrentPasswordError.setText("Password - Incorrect");
            flag = false;
        }
        
        
        // Checking if password was entered and correct - Presence, Valid, Logic
        if(pfNewPassword.getText().isEmpty() && pfNewPasswordRetype.getText().isEmpty()){
            lblNewPasswordError.setText("Password - Please provide.");
            lblNewPasswordRetypeError.setText("Password - Please provide");
            flag = false;
        }        
        else if(pfNewPassword.getText().isEmpty() && !pfNewPasswordRetype.getText().isEmpty()){
            lblNewPasswordRetypeError.setText("Password - Please re-enter");
            flag = false;
        }        
        else if(!pfNewPassword.getText().isEmpty() && pfNewPasswordRetype.getText().isEmpty()){
            lblNewPasswordError.setText("Password - Please re-enter");
            flag = false;
        }else{
            if(!check.checkPassword(pfNewPassword.getText(), pfNewPasswordRetype.getText())){
                lblNewPasswordError.setText("Password - Does not match");
                lblNewPasswordRetypeError.setText("Re-enter Password - Does not match.");
                flag = false;
            }
        }
        
        if(flag){
            account.updatePassword(currentUser, pfNewPassword.getText());
        }
        
        
    }
    
}
