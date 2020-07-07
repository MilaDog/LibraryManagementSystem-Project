/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class AccountController implements Initializable {

    private String currentUser = "";
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneDetailsBackground;
    @FXML
    private Label lblAccountFirstName;
    @FXML
    private Label lblAccountDOB;
    @FXML
    private Label lblAccountEmail;
    @FXML
    private Label lblAccountSurname;
    @FXML
    private Label lblAcountPhone;
    @FXML
    private Label lblAccountFirstNameView;
    @FXML
    private Label lblAccountDOBView;
    @FXML
    private Label lblAccountSurnameView;
    @FXML
    private Label lblAccountPhoneView;
    @FXML
    private Label lblAccountEmailView;
    @FXML
    private BorderPane bpAccountDetailsChange;
    @FXML
    private Label lblAccountPassword;
    @FXML
    private Label lblAccountPasswordEdit;

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
    private void lblAccountPasswordEdit(MouseEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        
        try{
            loader = new FXMLLoader(getClass().getResource("/stages/AccountPasswordEdit.fxml"));
            root = (Parent) loader.load();
            
            AccountPasswordEditController accountPasswordEditController = loader.getController();
            accountPasswordEditController.currentUser(currentUser);
            
        }catch(IOException err){
            err.printStackTrace();
        }
        
        bpAccountDetailsChange.setCenter(root);
    }
    
}
