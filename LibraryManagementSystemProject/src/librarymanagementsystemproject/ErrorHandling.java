package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 11 Jul 2020

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ErrorHandling {
    
    private void displayErrorDialog(Stage stage, String dialog){
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText(dialog);
        alert.showAndWait();
    }
    
    public void bookIssueError(Stage stage, String errorType){
        if(errorType.equalsIgnoreCase("member")){
            String dialog = "Did not select a Member.";
            displayErrorDialog(stage, dialog);
        }else if(errorType.equalsIgnoreCase("book")){
            String dialog = "Did not select a Book.";
            displayErrorDialog(stage, dialog);
        }else{
            String dialog = "Did not select a Book/User.";
            displayErrorDialog(stage, dialog);
        }
    }
    
    public void bookReturnError(Stage stage){
        String dialog = "Did not select a Book to return.";
        displayErrorDialog(stage, dialog);
    }
    
    public void staffAddError(Stage stage){
        String dialog = "Did not select a Member.";
        displayErrorDialog(stage, dialog);        
    }
    
    public void staffRemoveError(Stage stage){ 
        String dialog = "Did not select a Staff Member.";
        displayErrorDialog(stage, dialog);        
    }
    
    public void requestBookError(Stage stage){  
        String dialog = "Did not select a Book.";
        displayErrorDialog(stage, dialog);       
    }

}// END ErrorHandling class
