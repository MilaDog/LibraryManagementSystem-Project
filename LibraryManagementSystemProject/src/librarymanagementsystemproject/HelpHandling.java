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
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(dialog);
        alert.showAndWait();
    }

    /**
     *
     * @param stage Takes in ListAvailableBooks stage
     */
    
    public void libraryAvailableBooksHelp(Stage stage){
        String dialog = "1- Enter Title/Authors/ISBN in Text Field.\n2- Click 'Search' to search the Library for what you are looking for.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in ListBooks stage
     */
    public void libraryBooksHelp(Stage stage){
        String dialog = "1- Enter Title/Authors/ISBN in Text Field.\n2- Click 'Search' to search the Library for what you are looking for.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in RequestBook stage
     */
    public void libraryRequestBookHelp(Stage stage){
        String dialog = "1- Select either 'Title', 'Author' or 'ISBN'. This is used to filter the search.\n2- Enter in either the book's Title, Authors or ISBN.\n3- Click 'Search' to search for the book you wish to have.\n4- If found, select the book in the table, then click 'Request Book' to have it requested.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in ReturnDates stage
     */
    public void libraryReturnDatesHelp(Stage stage){
        String dialog = "1- Enter Title/Authors/BookID/TakeoutID in Text Field.\n2- Click 'Search' to search the books you have taken out to find their respected return dates.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in TakenOut stage
     */
    public void libraryTakenOutHelp(Stage stage){
        String dialog = "1- Enter Title/Authors/BookID/TakeoutID in Text Field.\n2- Click 'Search' to search the books you have taken out to see if you have taken that book out.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in BookIssue stage
     */
    public void booksIssueMemberHelp(Stage stage){
        String dialog = "1- Enter in the MemberID/Name/Surname of whoever you wish to find.\n2- Click 'Search' to search for them.\n3- When the person you want to find is found, select the Member in the table.";
        displayHelpDialog(stage, dialog);        
    }
    
    /**
     *
     * @param stage Takes in BookIssue stage
     */
    public void booksIssueBookHelp(Stage stage){
        String dialog = "1- Enter in the BookID/Title/Author of the book you wish to find.\n2- Click 'Search' to search for if that book is available.\n3- When the book you want to find is found, select the Book in the table.";
        displayHelpDialog(stage, dialog);
    }
    
    /**
     *
     * @param stage Takes in BookIssue stage
     */
    public void booksIssueHelp(Stage stage){
        String dialog = "When both the respected Member and Book is selected in the tables, click 'Issue Book' to issue it to them.";
        displayHelpDialog(stage, dialog);
    }
    
    /**
     *
     * @param stage Takes in BookReturn stage
     */
    public void booksReturnMemberHelp(Stage stage){
        String dialog = "1- Enter in the MemberID/Name/Surname of whoever you wish to find.\n2- Click 'Search' to search for them.\n3- When the person you want to find is found, select the Member in the table.";
        displayHelpDialog(stage, dialog);    
    }
    
    /**
     *
     * @param stage Takes in BookReturn stage
     */
    public void booksReturnBooksHelp(Stage stage){
        String dialog = "1- Enter in the TakeoutID/BookID/UserID of the book you wish to find.\n2- Click 'Search' to search for the respected taken out books.\n3- When the book you want to find is found, select the Book in the table.\n\nPlease note: If a Member was selected in the Member table, only the books they have taken out will be displayed.";
        displayHelpDialog(stage, dialog);
    }
    
    /**
     *
     * @param stage Takes in BookReturn stage
     */
    public void booksReturnHelp(Stage stage){
        String dialog = "When both the respected Member and Book is selected in the tables, click 'Return Book' to mark the book as returned.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in FixBook stage
     */
    public void booksFixHelp(Stage stage){
        String dialog = "1- Enter Title/Authors/BookID in Text Field.\n2- Click 'Search' to search the Library for what you are looking for.\n3- When found, select the book from the table.\n4- Provide the reason for the Fix Request - the issue that needs to be fixed.\n5- Click 'Request Fix' to request a book fix for that book.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in FixedBook stage
     */
    public void booksFixedHelp(Stage stage){
        String dialog = "1- Enter Title/Authors/BookID in Text Field.\n2- Click 'Search' to search the Library for what you are looking for.\n3- When found, select the book from the table.\n4- Click 'Mark as Fixed' to mark that book as fixed.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in BooksOutstanding stage
     */
    public void booksOutstandingHelp(Stage stage){
        String dialog = "1- Enter TakeoutID/BookID/UserID in Text Field.\n2- Click 'Search' to search all of the outstanding books.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in RequestBook stage
     */
    public void booksRequestedHelp(Stage stage){
        String dialog = "1- Enter RequestID/Title/Authors/ISBN10/ISBN13 in Text Field.\n2- Click 'Search' to search all of the requested books.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in ManageStaffAdd stage
     */
    public void staffAddHelp(Stage stage){
        String dialog = "1- Click on a Member in the table.\n2- Click 'Add Staff' to add them to the Staff List";
        displayHelpDialog(stage, dialog);
    }
    
    /**
     *
     * @param stage Takes in ManageStaffRemove stage
     */
    public void staffRemoveHelp(Stage stage){
        String dialog = "1- Click on a Member in the table.\n2- Click 'Add Staff' to add them to the Staff List\n\nPlease note: The current Staff member is not viewable in this table. You cannot remove yourself from the Staff List.";
        displayHelpDialog(stage, dialog);
        
    }
    
    /**
     *
     * @param stage Takes in ManageStaffView stage
     */
    public void staffViewHelp(Stage stage){
        String dialog = "1- Enter MemberID/Name/Surname in Text Field.\n2- Click 'Search' to search all of the Staff Members.";
        displayHelpDialog(stage, dialog);
        
    }
    
}
