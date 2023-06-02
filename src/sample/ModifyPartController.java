package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.InHouse;
import sample.Model.Inventory;
import sample.Model.Outsourced;
import sample.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the ModifyPartForm.
 *
 * @author Sharina V. Jones
 */
public class ModifyPartController implements Initializable {
    private Part selectedPart;
    private int index = 0;

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
     * Validates form data and updates a part generates an error
     * on save: the user is redirected to the MainForm
     * on error: the user gets and error message and remains on the AddPartForm
     * @param e the event when the radio button is selected
     */
    @FXML
    public void saveBtnClicked(ActionEvent e) throws IOException {
        try {
            int id = selectedPart.getId();
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            Part newPart;

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

                //if the user has switched part types
                //delete the old part
                //add a "new" part using the same ID
                if (partInRadio.isSelected() && selectedPart instanceof Outsourced) {
                    Inventory.deletePart(selectedPart);
                    int machineId = Integer.parseInt(toggleTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                } else if (partOutRadio.isSelected() && selectedPart instanceof InHouse) {
                    Inventory.deletePart(selectedPart);
                    String companyName = toggleTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                }

                //otherwise update the part
                else if (partInRadio.isSelected()) {
                    int machineId = Integer.parseInt(toggleTxt.getText());
                    newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(index, newPart);
                } else if (partOutRadio.isSelected()) {
                    String companyName = toggleTxt.getText();
                    if(companyName.isEmpty() || companyName == null) {
                        showErrors(9);
                        return;
                    }
                    newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(index, newPart);
                }

                primaryStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                primaryStage.setTitle("Inventory");
                primaryStage.setScene(new Scene(root, 800, 500));
                primaryStage.show();
            }
        }catch (NumberFormatException exc) {
            showErrors(8);
        }

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
     * Loads the part information from the MainForm into the view
     * Establishes the index for the updatePart function
     * @param url points to the resource
     * @param rb    the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load the parts info
        selectedPart = MainController.getSelectedPart();
        int testIndex = 0;

        //find the index of the part passed in
        for(Part part: Inventory.getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                index = testIndex;
            } else {
                testIndex++;
            }
        }

        //choose the correct radio button and label display
        if(selectedPart instanceof InHouse) {
            partInRadio.setSelected(true);
            toggleLbl.setText("Machine ID");
            toggleTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else if (selectedPart instanceof Outsourced) {
            partOutRadio.setSelected(true);
            toggleLbl.setText("Company Name");
            toggleTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }

        //set the field values
        idTxt.setPromptText(String.valueOf(selectedPart.getId()));
        nameTxt.setText(selectedPart.getName());
        invTxt.setText(String.valueOf(selectedPart.getStock()));
        priceTxt.setText(String.valueOf(selectedPart.getPrice()));
        maxTxt.setText(String.valueOf(selectedPart.getMax()));
        minTxt.setText(String.valueOf(selectedPart.getMin()));

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
