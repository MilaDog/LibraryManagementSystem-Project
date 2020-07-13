/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Daniel
 */
public class ManageStaffAddController implements Initializable {
    
    private ArrayList<RegisteredUsers> registeredUsersNotStaff = new ArrayList<>();

    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    private Checks check = new Checks();
    private ErrorHandling errorHandler = new ErrorHandling();
    private HelpHandling helpHandler = new HelpHandling();
    
    private Stage stageStaffAdd = null;
    
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
    private Button btnTest;

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
                
                tblViewMembers.getItems().clear();

                registeredUsersNotStaff = lib.fetchMembersNotStaff();                 
                displayMembers(registeredUsersNotStaff);                            
            }
        }); 
    }
    
    private void displayMembers(ArrayList<RegisteredUsers> users){   
        
        tblViewMembers.getItems().clear();

        // If nothing was found, display all . Else, display what was found
        if(users.isEmpty()){            
            tblViewMembers.setPlaceholder(new Label("There are no Registered Users"));
        }else{        
            colMemberID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colMemberFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colMemberSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            ObservableList<RegisteredUsers> allUsers = FXCollections.observableArrayList(users);
            tblViewMembers.setItems(allUsers);
        }// END if-else - any found books
    }

    @FXML
    private void btnMemberSearchClicked(MouseEvent event) {   
        
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
                }
            }
            
            if(users.isEmpty()){
                tblViewMembers.setPlaceholder(new Label("Did not find any member"));   
            }else{
                displayMembers(users);
            }
            
        }// END if-else - anything to search 
    }

    @FXML
    private void btnStaffAddRemoveClicked(MouseEvent event) {        
        if(tblViewMembers.getSelectionModel().getSelectedItem() != null){
            RegisteredUsers selectedUser = tblViewMembers.getSelectionModel().getSelectedItem();
            
            libActions.addStaff(selectedUser);
            
            // Resetting - user made staff, so remove their name from the table
            registeredUsersNotStaff = lib.fetchMembersNotStaff(); 
            displayMembers(registeredUsersNotStaff);
            
        }else{
            errorHandler.staffAddError(stageStaffAdd);
        }
    }

    @FXML
    private void btnTestClicked(MouseEvent event) {
        helpHandler.staffAddHelp(stageStaffAdd);
    }
    
}
