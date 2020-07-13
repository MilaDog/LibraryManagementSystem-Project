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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class SettingsController implements Initializable {

    private Stage stageSettings = null;
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private HBox hBoxSettings;
    @FXML
    private ImageView imSettingsLogout;
    @FXML
    private Label lblSettingsLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Platform.runLater(new Runnable() {
            @Override
            public void run(){            
                
                // Getting the Member stage
                stageSettings = (Stage) anchorPaneBackground.getScene().getWindow();                
            }
        });
    }    

    @FXML
    private void hBoxSettingsClicked(MouseEvent event) {
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
            stageSettings.hide();
        }catch(IOException err){
            err.printStackTrace();
        }
    }
    
}
