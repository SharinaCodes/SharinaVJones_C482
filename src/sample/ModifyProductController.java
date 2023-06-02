package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the ModifyProductForm.
 *
 * @author Sharina V. Jones
 */
public class ModifyProductController implements Initializable {
    private Product selectedProduct;
    private int index = 0;

    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField searchTxt;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    /**
     * Parts search input field
     */
    @FXML
    private TextField partsSearch;
    /**
     * Parts table view
     */
    @FXML
    private TableView<Part> partsTableView;
    /**
     * Parts table id column
     */
    @FXML
    private TableColumn<Part, Integer> partsId;
    /**
     * Parts table name column
     */
    @FXML
    private TableColumn<Part, String> partsName;
    /**
     * Parts table inventory column
     */
    @FXML
    private TableColumn<Part, Integer> partsInv;
    /**
     * Parts table price column
     */
    @FXML
    private TableColumn<Part, Double> partsPrice;
    /**
     * Add part button
     */
    @FXML
    private Button addPartBtn;
    /**
     * Modify part button
     */
    @FXML
    private Button modifyPartBtn;
    /**
     * Delete part button
     */
    @FXML
    private Button deletePartBtn;
    /**
     * Products table view
     */

    @FXML
    private TableView<Part> assocPartsTableView;
    /**
     * Parts table id column
     */
    @FXML
    private TableColumn<Part, Integer> assocPartsId;
    /**
     * Parts table name column
     */
    @FXML
    private TableColumn<Part, String> assocPartsName;
    /**
     * Parts table inventory column
     */
    @FXML
    private TableColumn<Part, Integer> assocPartsInv;
    /**
     * Parts table price column
     */
    @FXML
    private TableColumn<Part, Double> assocPartsPrice;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**
     * RUNTIME ERROR: Initially I set assocPart to equal selectedProduct.associatedParts. This caused an error where
     * if the user modified the associated parts and cancelled without saving, the associated parts were modified
     * anyway. I fixed this error by passing passing in the individual parts, so that assocParts and selectedProduct
     * .associatedParts were not referencing the same object.
     * Loads the parts from the selected to the partsTableView and the assocPartsTable view
     * Loads the info from the selected part to the form
     * @param url points to the resource
     * @param rb    the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load the product info
        selectedProduct = MainController.getSelectedProduct();
        int testIndex = 0;

        //find the index of the product passed in
        for(Product product: Inventory.getAllProducts()) {
            if (product.getId() == selectedProduct.getId()) {
                index = testIndex;
            } else {
                testIndex++;
            }
        }

        //set the field values
        idTxt.setPromptText(String.valueOf(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        invTxt.setText(String.valueOf(selectedProduct.getStock()));
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        maxTxt.setText(String.valueOf(selectedProduct.getMax()));
        minTxt.setText(String.valueOf(selectedProduct.getMin()));

        //load the parts table data
        partsTableView.setItems(Inventory.getAllParts());

        partsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //load the associated parts table
        assocPartsTableView.setItems(selectedProduct.getAllAssociatedParts());

        assocPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add to local variable
        for(Part part: selectedProduct.getAllAssociatedParts()) {
            assocParts.add(part);
        }
    }

    /**
     * Adds a part to the associated parts of a Product
     * on error: generates an error message
     * @param e the event when the button is clicked
     */
    @FXML
    public void addPartClicked(ActionEvent e) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showErrors(1);

        } else {
            assocParts.add(selectedPart);
            assocPartsTableView.setItems(assocParts);

            assocPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
            assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
            assocPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assocPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * Removes a part from the associated parts of a Product
     * on error: generates an error message
     * on add: confirms the user wants to remove the part
     * @param e the event when the button is clicked
     */
    @FXML
    public void removePartClicked(ActionEvent e) throws IOException {
        Part selectedPart = assocPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showErrors(1);

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setContentText("Do you want to remove this associated part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK){
                assocParts.remove(selectedPart);
                assocPartsTableView.setItems(assocParts);

                assocPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
                assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
                assocPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
                assocPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            }

        }
    }

    /**
     * Ensures all inventory parts are listed if the search field is blank
     * @param e the event data is typed into the search field
     */
    @FXML
    public void partSearchType(KeyEvent e) {
        if(partsSearch.getText().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Searches for a Part by part id or part name
     * on error: generates an error message
     * on part found: updates the partsTableView
     * on part not found: generates an error
     * @param e the event when the button is clicked
     */
    @FXML
    public void partSearchClicked(ActionEvent e) throws  IOException {
        ObservableList<Part> partInventory = FXCollections.observableArrayList();
        String searchString = partsSearch.getText();
        if(searchString.isEmpty()) {
            showErrors(1);
            return;
        } else {
            if (isInteger(searchString)) {
                int searchId = Integer.parseInt(searchString);
                Part foundPart = Inventory.lookupPart(searchId);
                partInventory.add(foundPart);
                partsTableView.setItems(partInventory);

                if (foundPart == null) {
                    partsTableView.setItems(Inventory.getAllParts());
                    showErrors(2);
                    return;
                }
            } else {
                partInventory = Inventory.lookupPart(searchString);
                if(partInventory.isEmpty() || partInventory == null) {
                    showErrors(2);
                    partsTableView.setItems(Inventory.getAllParts());
                    return;
                } else {
                    partsTableView.setItems(partInventory);
                }
            }
        }
    }

    /**
     * Attempts to convert a string to an integer
     * on success: returns true
     * on fail: returns false
     * @param id the string that is to be converted
     */
    private boolean isInteger(String id) {
        try {
            Integer.parseInt(id);
            return true;

        } catch (Exception exc) {
            return false;
        }
    }

    /**
     * Changes to the MainForm view without saving any changes
     * @param e the event when the button is clicked
     */
    @FXML
    public void cancelBtnClicked(ActionEvent e) throws IOException {
        Stage primaryStage = (Stage)((Button)e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    /**
     * Validates form data and updates a product or generates an error
     * on save: the user is redirected to the MainForm
     * on error: the user gets and error message and remains on the ModifyProductForm
     * @param e the event when the radio button is selected
     */
    @FXML
    public void saveBtnClicked(ActionEvent e) throws IOException {
        try {
            int id = selectedProduct.getId();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            Product newProduct = new Product(id, name, price, stock, min, max);

            if (name.isEmpty() || name == null) {
                showErrors(7);
                return;
            } else if (min > max) {
                showErrors(5);
                return;
            } else if ((stock > max) || (stock < min)) {
                showErrors(6);
                return;
            } else {

                for (Part part : assocParts) {
                    newProduct.addAssociatedPart(part);
                }

                Inventory.updateProduct(index, newProduct);

                Stage primaryStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                primaryStage.setTitle("Inventory");
                primaryStage.setScene(new Scene(root, 800, 500));
                primaryStage.show();
            }
        } catch (NumberFormatException exc) {
            showErrors(8);
        }

    }

    /**
     * Generates a number of different errors and warning based on the number passed in
     * @param errNum the number of the error to be displayed
     */
    private void showErrors(int errNum) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch(errNum) {
            case 1:
                alert.setTitle("Warning");
                alert.setHeaderText("No part selected");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Warning");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Warning");
                alert.setHeaderText("No product selected");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Warning");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Min must be less than max");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("Inv must be between Min and Max");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please enter a Name");
                alertError.showAndWait();
                break;
            case 8:
                alertError.setTitle("Error");
                alertError.setHeaderText("Inv, Price, Min, Max, and Machine ID should be numbers");
                alertError.showAndWait();
                break;
            case 9:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please enter a Company Name");
                alertError.showAndWait();
                break;
        }
    }

}
