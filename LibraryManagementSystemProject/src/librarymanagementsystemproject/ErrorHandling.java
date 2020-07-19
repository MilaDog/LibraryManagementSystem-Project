package librarymanagementsystemproject;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class ErrorHandling {
    
    // Method used for displaying the error dialog
    private void displayErrorDialog(Stage stage, String dialog){
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(dialog);
        alert.showAndWait();
    }
    
    /**
     *
     * @param stage Takes in the BookIssue stage
     * @param errorType Takes in the type of error
     */
    public void bookIssueError(Stage stage, String errorType){
        if(errorType.equalsIgnoreCase("member")){
            String dialog = "Did not select a Member.";
            displayErrorDialog(stage, dialog);
        }else if(errorType.equalsIgnoreCase("book")){
            String dialog = "Did not select a Book.";
            displayErrorDialog(stage, dialog);
        }else{
            String dialog = "Did not select a Book and User.";
            displayErrorDialog(stage, dialog);
        }
    }
    
    /**
     *
     * @param stage Takes in the BooksReturn stage
     */
    public void bookReturnError(Stage stage){
        String dialog = "Did not select a Book to return.";
        displayErrorDialog(stage, dialog);
    }
    
    /**
     *
     * @param stage Takes in the ManageStaffAdd stage
     */
    public void staffAddError(Stage stage){
        String dialog = "Did not select a Member.";
        displayErrorDialog(stage, dialog);        
    }
    
    /**
     *
     * @param stage Takes in the ManageStaffRemove stage
     */
    public void staffRemoveError(Stage stage){ 
        String dialog = "Did not select a Staff Member.";
        displayErrorDialog(stage, dialog);        
    }
    
    /**
     *
     * @param stage Takes in the RequestBook stage
     */
    public void requestBookError(Stage stage){  
        String dialog = "Did not select a Book to request.";
        displayErrorDialog(stage, dialog);       
    }
    
    /**
     *
     * @param stage Takes in the FixBook stage
     * @param errorType Takes in the type of error
     */
    public void fixBookError(Stage stage, String errorType){
        if(errorType.equalsIgnoreCase("book")){
            String dialog = "Did not select a Book.";
            displayErrorDialog(stage, dialog);
        }else if(errorType.equalsIgnoreCase("reason")){
            String dialog = "Did not give a fix reason.";
            displayErrorDialog(stage, dialog);
        }else{
            String dialog = "Did not select a Book and no fix reason given.";
            displayErrorDialog(stage, dialog);
        }
    }
    
    /**
     *
     * @param stage Takes in the FixedBooks stage
     */
    public void fixedBooksError(Stage stage){  
        String dialog = "Did not select a Book to mark as fixed.";
        displayErrorDialog(stage, dialog);       
    }

}// END ErrorHandling class
