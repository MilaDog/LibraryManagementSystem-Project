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
import librarymanagementsystemproject.Library;
import librarymanagementsystemproject.LibraryActions;
import librarymanagementsystemproject.Staff;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class ManageStaffViewController implements Initializable {
    
    private ArrayList<Staff> staffMembers = new ArrayList<>();

    private Library lib = new Library();
    private LibraryActions libActions = new LibraryActions();
    
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){     
                tblViewStaff.getItems().clear();

                staffMembers = lib.fetchAllStaff();        
                displayMembers(staffMembers);                            
            }
        });  
    }    
    
    private void displayMembers(ArrayList<Staff> staff){

        // If nothing was found, display all . Else, display what was found
        if(staff.isEmpty()){            
            tblViewStaff.setPlaceholder(new Label("There are no Staff Members"));
        }else{        
            colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
            colMemberID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colMemberFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colMemberSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            ObservableList<Staff> allStaff = FXCollections.observableArrayList(staff);
            tblViewStaff.setItems(allStaff);
        }// END if-else - any found books
    }

    @FXML
    private void btnMemberSearchClicked(MouseEvent event) {   
        
        tblViewStaff.getItems().clear();
        
        
        // If search text field is empty, displays all registered users. If not, finds a user similar to what is being searched.
        if(txfMemberSearchInput.getText().isEmpty()){
            ArrayList<Staff> allStaff = lib.fetchAllStaff();
            displayMembers(allStaff);
        }else{
            ArrayList<Staff> foundStaffMembers = new ArrayList<>();
            foundStaffMembers.clear();
            
            for(Staff staff: staffMembers){
                if(staff.getUserID().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    foundStaffMembers.add(staff);
                }else if(staff.getFirstName().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    foundStaffMembers.add(staff);
                }else if(staff.getSurname().toLowerCase().contains(txfMemberSearchInput.getText().toLowerCase())){
                    foundStaffMembers.add(staff);
                }
            }
            
            if(foundStaffMembers.isEmpty()){
                tblViewStaff.setPlaceholder(new Label("Did not find any Staff Member(s)"));   
            }else{
                displayMembers(foundStaffMembers);
            }
            
        }// END if-else - anything to search   
    }
    
}
