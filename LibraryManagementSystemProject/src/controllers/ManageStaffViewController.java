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
import librarymanagementsystemproject.HelpHandling;
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.Staff;

/**
 * FXML Controller class
 *
 * @author Daniel Ryan Sergeant
 */
public class ManageStaffViewController implements Initializable {
    
    // Initializing necessary ArrayLists
    private ArrayList<Staff> staffMembers = new ArrayList<>();

    //Instantiating necessary Objects
    private Library lib = new Library();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageStaffView = new Stage();
    
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
    private TableView<Staff> tblViewStaff;
    @FXML
    private TableColumn<Staff, String> colStaffID;
    @FXML
    private TableColumn<Staff, String> colMemberID;
    @FXML
    private TableColumn<Staff, String> colMemberFirstName;
    @FXML
    private TableColumn<Staff, String> colMemberSurname;
    @FXML
    private TableColumn<Staff, String> colMemberEmail;
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
                
                // Getting the StaffRemove stage
                stageStaffView = (Stage) anchorPaneBackground.getScene().getWindow();  
                
                // Clearing TableView
                tblViewStaff.getItems().clear();

                // Fetchin data from PostgreDB - all Staff members
                staffMembers = lib.fetchAllStaff();        
                
                // Displaying data
                displayMembers(staffMembers);                            
            }
        });  
    }    
    
    private void displayMembers(ArrayList<Staff> staff){  
        
        // Clearing TableView
        tblViewStaff.getItems().clear();

        // If nothing was found, display all . Else, display what was found
        if(staff.isEmpty()){            
            tblViewStaff.setPlaceholder(new Label("There are no Staff Members"));
        }else{   
            // Creating the CellValues for the table, so that each cell in the table gets the correct data from the Object      
            colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
            colMemberID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colMemberFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colMemberSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            // Converting the ArrayList to ObservableList as TableView does not take ArrayLists as a param. Then displaying all data
            ObservableList<Staff> allStaff = FXCollections.observableArrayList(staff);
            tblViewStaff.setItems(allStaff);
        }// END if-else - any found books
    }

    @FXML
    private void btnMemberSearchClicked(MouseEvent event) {   
        
        // Clearing TableView
        tblViewStaff.getItems().clear();
        
        
        // If search text field is empty, displays all registered users. If not, finds a user similar to what is being searched.
        if(txfMemberSearchInput.getText().isEmpty()){
            ArrayList<Staff> allStaff = lib.fetchAllStaff();
            displayMembers(allStaff);
        }else{
            // Initializing a temporary ArrayList to store the search results
            ArrayList<Staff> foundStaffMembers = new ArrayList<>();
            staffMembers.clear();
            
            for(Staff staff: staffMembers){
                if(staff.getUserID().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    foundStaffMembers.add(staff);
                }else if(staff.getFirstName().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    foundStaffMembers.add(staff);
                }else if(staff.getSurname().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    foundStaffMembers.add(staff);
                }// END if-else - search filter
            }// END loop
            
            if(foundStaffMembers.isEmpty()){
                tblViewStaff.setPlaceholder(new Label("Did not find any Staff Member(s)"));   
            }else{
                displayMembers(foundStaffMembers);
            }// END if-else - display search results
            
        }// END if-else - anything to search   
    }

    
    // Below is the HelpIcon event - displays respective help dialog
    @FXML
    private void imHelpClicked(MouseEvent event) {
        helpHandler.staffViewHelp(stageStaffView);
    }
    
}
