package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.InHouse;
import sample.Model.Inventory;
import sample.Model.Outsourced;

import java.io.IOException;

/**
 * This class is the controller for the AddPartForm.
 *
 * @author Sharina V. Jones
 */
public class AddPartController {
    private Parent root;
    private Stage primaryStage;

    @FXML
    private RadioButton partInRadio;
    @FXML
    private RadioButton partOutRadio;
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
    private ToggleGroup togglePart;
    @FXML
    private Label toggleLbl;
    @FXML
    private TextField toggleTxt;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;


    /**
     * Changes the toggleLbl to "Machine ID" when the In-House radio button is clicked
     * @param e the event when the radio button is selected
     */
    @FXML
    public void partInRadioSelected(ActionEvent e) {
        toggleLbl.setText("Machine ID");
    }

    /**
     * Changes the toggleLbl to "Company Name" when the Outsourced radio button is clicked
     * @param e the event when the radio button is selected
     */
    @FXML
    public void partOutRadioSelected(ActionEvent e) {
        toggleLbl.setText("Company Name");
    }

    /**
     * Exits to the MainForm without saving any changes
     * @param e the event when the radio button is selected
     */
    @FXML
    public void cancelBtnClicked(ActionEvent e) throws IOException {
        primaryStage = (Stage)((Button)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        primaryStage.setTitle("Add Part");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    /**
     * Validates form data and saves it or generates an error
     * on save: the user is redirected to the MainForm
     * on error: the user gets and error message and remains on the AddPartForm
     * @param e the event when the radio button is selected
     */
    @FXML
    public void saveBtnClicked(ActionEvent e) throws IOException {
        try {
            int id = Inventory.createPartID();
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

            if(name.isEmpty() || name == null) {
                showErrors(7);
                return;
            } else if (min > max) {
                showErrors(5);
                return;
            } else if((stock > max) || (stock < min)) {
                showErrors(6);
                return;
            } else {
                if(partInRadio.isSelected()) {
                    int machineId = Integer.parseInt(toggleTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else if (partOutRadio.isSelected()) {
                    String  companyName = toggleTxt.getText();
                    if(companyName.isEmpty() || companyName == null) {
                        showErrors(9);
                        return;
                    }
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }

                primaryStage = (Stage)((Button)e.getSource()).getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
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
