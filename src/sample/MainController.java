package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.*;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the MainForm.
 *
 * @author Sharina V. Jones
 */
public class MainController implements Initializable {

    @FXML
    private TextField partsSearch;
    @FXML 
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsId;
    @FXML
    private TableColumn<Part, String> partsName;
    @FXML
    private TableColumn<Part, Integer> partsInv;
    @FXML
    private TableColumn<Part, Double> partsPrice;
    @FXML
    private Button addPartBtn;
    @FXML
    private Button modifyPartBtn;
    @FXML
    private Button deletePartBtn;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsId;
    @FXML
    private TableColumn<Product, String> productsName;
    @FXML
    private TableColumn<Product, Integer> productsInv;
    @FXML
    private TableColumn<Product, Double> productsPrice;
    @FXML
    private Button addProductBtn;
    @FXML
    private Button modifyProductBtn;
   @FXML
    private Button deleteProductBtn;
    @FXML
    private TextField prodSearchTxt;

    private static Part selectedPart;
    private static Product selectedProduct;


    /**
     * Allows the selectedPart variable to be accessed from other forms
     * @return selectedPart the part that has been selected
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * Allows the selectedProduct variable to be accessed from other forms
     * @return selectedProduct the part that has been selected
     */
    public static Product getSelectedProduct(){
        return selectedProduct;
    }


    /**
     * Initializes the form by setting the data for the partsTableView and
     * the productsTableView
     * @param url points to the resource
     * @param rb    the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load the parts table data
        partsTableView.setItems(Inventory.getAllParts());

        partsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //load the products table data
        productsTableView.setItems(Inventory.getAllProducts());

        productsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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
     * Ensures all inventory products are listed if the search field is blank
     * @param e the event data is typed into the search field
     */
    public void prodSearchType(KeyEvent e) {
        if(prodSearchTxt.getText().isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Changes to the AddPartForm view
     * @param e the event when the button is clicked
     */
    @FXML
    public void addPartClicked(ActionEvent e) throws IOException {
        Stage primaryStage = (Stage)((Button)e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        primaryStage.setTitle("Add Part");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    /**
     * Changes to the AddProductForm view
     * @param e the event when the button is clicked
     */
    @FXML
    public void addProdClicked(ActionEvent e) throws IOException {
        Stage primaryStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        primaryStage.setTitle("Add Product");
        primaryStage.setScene(new Scene(root, 900, 650));
        primaryStage.show();
    }


    /**
     * Changes to the ModifyPartForm view
     * Sets the selectedPart variable needed by the view
     * @param e the event when the button is clicked
     */
    @FXML
    public void modPartClicked(ActionEvent e) throws IOException {
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            showErrors(1);
        } else {
            Stage primaryStage = (Stage)((Button)e.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("ModifyPartForm.fxml"));
            primaryStage.setTitle("Modify Part");
            primaryStage.setScene(new Scene(root, 800, 500));
            primaryStage.show();
        }
    }

    /**
     * Changes to the ModifyProdcutForm view
     * Sets the selectedProduct variable needed by the view
     * @param e the event when the button is clicked
     */
    @FXML
    public void modProdClicked(ActionEvent e) throws IOException {
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            showErrors(3);
        } else {
            Stage primaryStage = (Stage)((Button)e.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("ModifyProductForm.fxml"));
            primaryStage.setTitle("Modify Product");
            primaryStage.setScene(new Scene(root, 900, 650));
            primaryStage.show();
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
     * Searches for a Product by product id or product name
     * on error: generates an error message
     * on part found: updates the productsTableView
     * on part not found: generates an error
     * @param e the event when the button is clicked
     */
    @FXML
    public void prodSearchClicked(ActionEvent e) throws  IOException {
        ObservableList<Product> productInventory = FXCollections.observableArrayList();
        String searchString = prodSearchTxt.getText();

        if(searchString.isEmpty()) {
            showErrors(3);
            return;
        } else {
            if (isInteger(searchString)) {
                int searchId = Integer.parseInt(searchString);
                Product foundProd = Inventory.lookupProduct(searchId);
                productInventory.add(foundProd);
                productsTableView.setItems(productInventory);

                if (foundProd == null) {
                    productsTableView.setItems(Inventory.getAllProducts());
                    showErrors(4);
                    return;
                }
            } else {
                productInventory = Inventory.lookupProduct(searchString);
                if(productInventory.isEmpty() || productInventory == null) {
                    showErrors(4);
                    productsTableView.setItems(Inventory.getAllProducts());
                    return;
                } else {
                    productsTableView.setItems(productInventory);
                }
            }
        }
    }


    /**
     * Removes a part from the Inventory
     * on error: generates an error message
     * on delete: confirms the user wants to remove the part
     * @param e the event when the button is clicked
     */
    @FXML
    public void deletePartClicked(ActionEvent e) throws IOException {
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            showErrors(1);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK){
                if (Inventory.deletePart(selectedPart) == false) {
                    showErrors(2);
                };
            }
        }
    }

    /**
     * Removes a Product from the Inventory
     * on error: generates an error message
     * on delete: confirms the user wants to remove the part
     * validation: does not allow a user to delete a Product with associated Parts
     * @param e the event when the button is clicked
     */
    @FXML
    public void deleteProdClicked(ActionEvent e) throws IOException {
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            showErrors(3);
        } else {
            if(!selectedProduct.getAllAssociatedParts().isEmpty()) {
                showErrors(10);
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Please Confirm");
                alert.setContentText("Do you want to remove the selected product?");
                Optional<ButtonType> answer = alert.showAndWait();

                if(answer.isPresent() && answer.get() == ButtonType.OK){
                    if (Inventory.deleteProduct(selectedProduct) == false) {
                        showErrors(2);
                    }
                }

            };
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
     * Closes the application
     * @param e the event when the button is clicked
     */
    @FXML
    public void exitClicked(ActionEvent e) throws IOException {
        System.exit(0);
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
            case 10:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please remove associated parts before deleting");
                alertError.showAndWait();
                break;
        }
    }
}
