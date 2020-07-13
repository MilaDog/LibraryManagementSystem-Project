/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystemproject;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class HelpHandling {
    
    private void displayHelpDialog(Stage stage, String dialog){
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setHeaderText(dialog);
        alert.getDialogPane().setContentText("");
        alert.showAndWait();
    }
    
    public void staffAddHelp(Stage stage){
        String dialog = "=> Click on a Member in the table.\n=> Click 'Add Staff' to add them to the Staff List";
        displayHelpDialog(stage, dialog);
    }
    
}
