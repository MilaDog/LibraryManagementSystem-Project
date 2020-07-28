package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagementsystemproject.Checks;
import librarymanagementsystemproject.ErrorHandling;
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.RegisteredUsers;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class ManageStaffAddController implements Initializable {
    
    // Initializing necessary ArrayLists
    private ArrayList<RegisteredUsers> registeredUsersNotStaff = new ArrayList<>();

    // Instantiating necessary Objects
    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private Checks check = new Checks();
    private ErrorHandling errorHandler = new ErrorHandling();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageStaffAdd = new Stage();
    
    @FXML
    private AnchorPane anchorPaneBackground;
    @FXML
    private AnchorPane anchorPaneOptions;
    @FXML
    private Button btnMemberSearch;
    @FXML
    private TextField txfMemberSearchInput;
    @FXML
    private AnchorPane anchorPaneDisplay;
    @FXML
    private TableView<RegisteredUsers> tblViewMembers;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberID;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberFirstName;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberSurname;
    @FXML
    private TableColumn<RegisteredUsers, String> colMemberEmail;
    @FXML
    private Button btnStaffAddRemove;
    @FXML
    private Label lblManageStaffInfo;
    @FXML
    private ImageView imHelp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){   
                
                // Getting the StaffAdd stage
                stageStaffAdd = (Stage) anchorPaneBackground.getScene().getWindow();  
                
                
                // Clearing ArrayList
                tblViewMembers.getItems().clear();

                // Fetching data from PostgreDB - all non-staff members
                registeredUsersNotStaff = lib.fetchMembersNotStaff();  
                
                // Displaying data
                displayMembers(registeredUsersNotStaff);                            
            }
        }); 
    }
    
    private void displayMembers(ArrayList<RegisteredUsers> users){   
        
        // Clearing TableView
        tblViewMembers.getItems().clear();

        // If nothing was found, display all . Else, display what was found
        if(users.isEmpty()){            
            tblViewMembers.setPlaceholder(new Label("There are no Registered Users"));
        }else{      
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object   
            colMemberID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colMemberFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colMemberSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<RegisteredUsers> allUsers = FXCollections.observableArrayList(users);
            tblViewMembers.setItems(allUsers);
        }// END if-else - any found books
    }

    @FXML
    private void btnMemberSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblViewMembers.getItems().clear();
        
        
        // If search text field is empty, displays all registered users. If not, finds a user similar to what is being searched.
        if(txfMemberSearchInput.getText().isEmpty()){
            ArrayList<RegisteredUsers> allUsers = lib.fetchAllMembers();
            displayMembers(allUsers);
        }else{
            ArrayList<RegisteredUsers> users = new ArrayList<>();
            users.clear();
            
            for(RegisteredUsers user: registeredUsersNotStaff){
                if(user.getUserID().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }else if(user.getFirstName().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }else if(user.getSurname().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    users.add(user);
                }// END if-else - search filter
            }// END loop
            
            
            // Displaying lable in TableView is no results were found. Else displaying the results
            if(users.isEmpty()){
                tblViewMembers.setPlaceholder(new Label("Did not find any member"));   
            }else{
                displayMembers(users);
            }// END chekcing results
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnStaffAddRemoveClicked(MouseEvent event) {        
        if(tblViewMembers.getSelectionModel().getSelectedItem() != null){
            // Getting selected Member
            RegisteredUsers selectedUser = tblViewMembers.getSelectionModel().getSelectedItem();
            
            // Adding Member to the staff list
            libActions.addStaff(selectedUser);
            
            // Resetting - user made staff, so remove their name from the table
            registeredUsersNotStaff = lib.fetchMembersNotStaff(); 
            displayMembers(registeredUsersNotStaff);
            
        }else{
            errorHandler.staffAddError(stageStaffAdd);
        }// END if-else - Staff Member add
    }

    
    // Below are the HelpIcon events - displays respective help dialog
    private void btnTestClicked(MouseEvent event) {
        helpHandler.staffAddHelp(stageStaffAdd);
    }

    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.staffAddHelp(stageStaffAdd);
    }
    
}
