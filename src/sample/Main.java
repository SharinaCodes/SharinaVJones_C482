package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.*;

/**
 * This class is the main class for the application.
 * JAVADOC FOLDER LOCATION: SharinaVJones_C482/javadoc
 *
 * @author Sharina V. Jones
 */
public class Main extends Application {

    /**
     * Initializes the JavaFX application
     * @param primaryStage the initial window in which the application opens
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    /**
     * FUTURE ENHANCEMENT: Allow the user to by pressing the Enter key, or allow search box to dynamically filter
     * results
     * Launches the JavaFX application and initializes the Inventory
     * @param args an optional list of arguments
     */
    public static void main(String[] args) {
        //creat new parts
        InHouse flour = new InHouse(Inventory.createPartID(), "flour", 2.99, 20, 0,
                25, 3);
        InHouse apple = new InHouse(Inventory.createPartID(), "apple", 3.99, 10, 5, 20, 3);
        Outsourced yeast = new Outsourced(Inventory.createPartID(), "yeast", 16.99, 3, 1,
                5, "Kroger");
        Outsourced sugar = new Outsourced(Inventory.createPartID(), "sugar", 20.99, 3, 1,
                5, "Food Lion");

        //add them to the Inventory
        Inventory.addPart(flour);
        Inventory.addPart(apple);
        Inventory.addPart(yeast);
        Inventory.addPart(sugar);

        //create a new product
        Product appleFritter = new Product(Inventory.createProductID(), "apple fritter", 3.55, 10,
                3, 20);

        //add associated parts
        appleFritter.addAssociatedPart(apple);
        appleFritter.addAssociatedPart(flour);
        appleFritter.addAssociatedPart(yeast);
        appleFritter.addAssociatedPart(sugar);

        //add the product to the inventory
        Inventory.addProduct(appleFritter);

        launch(args);
    }
}
