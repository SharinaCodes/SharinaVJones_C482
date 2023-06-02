package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Random;

/**
 * This class creates the Inventory model
 *
 * @author Sharina V. Jones
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to the Inventory
     * @param newPart the part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the Inventory
     * @param newProduct the part to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Uses the part id to find a part in the Inventory
     * @param partId the id of the part being searched for
     * @return part the located part
     */
    public static Part lookupPart(int partId) {
        for(Part part: allParts) {
            //if the part is found
            if(part.getId() == partId) {
                //return it
                return part;
            }
        }

        //otherwise return null
        return null;
    }

    /**
     * Uses the product id to find a product in the Inventory
     * @param productId the id of the product being searched for
     * @return product the located part
     */
    public static Product lookupProduct(int productId) {
        for(Product product: allProducts) {
            //if the product is found
            if(product.getId() == productId) {
                //return it
                return product;
            }
        }

        //otherwise return null
        return null;
    }

    /**
     * Uses the part name to find a part in the Inventory
     * @param partName the name of the part being searched for
     * @return matchingParts the located parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for(Part part: allParts) {
            if(part.getName().contains(partName)) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     * Uses the product name to find products in the Inventory
     * @param productName the name of the product being searched for
     * @return matchingProducts the located product
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for(Product product: allProducts) {
            if(product.getName().contains(productName)) {
                matchingProducts.add(product);
            }
        }

        return matchingProducts;
    }

    /**
     * Modifies a part in the inventory
     * @param index the name of the part to be modified
     * @param selectedPart the data to be added to the part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Modifies a product in the inventory
     * @param index the name of the product to be modified
     * @param newProduct the data to be added to the product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a part in the inventory
     * @param selectedPart the part to be deleted
     * @return returns true or false depending on if the delete was successful
     */
    public static boolean deletePart(Part selectedPart) {
        //if the part exists
        if(allParts.contains(selectedPart)) {
            //remove it
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes a product in the inventory
     * @param selectedProduct the product to be deleted
     * @return returns true or false depending on if the delete was successful
     */
    public static boolean deleteProduct(Product selectedProduct) {
        //if the product exists
        if(allProducts.contains(selectedProduct)) {
            //remove it
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets all parts in the Inventory
     * @return allParts a lists of all Inventory parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products in the Inventory
     * @return allProducts a lists of all Inventory products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Creates a unique partId
     * @return partId a unique part id
     */
    public static int createPartID() {
        Random rand = new Random();
        int partId = rand.nextInt(1000);

        if(Inventory.lookupPart(partId) == null) {
            return rand.nextInt(1000);
        } else {
            return createPartID();
        }

    }

    /**
     * Creates a unique productId
     * @return prodId a unique product id
     */
    public static int createProductID() {
        Random rand = new Random();
        int prodId = rand.nextInt(1000);

        if(Inventory.lookupPart(prodId) == null) {
            return rand.nextInt(1000);
        } else {
            return createProductID();
        }
    }

}
