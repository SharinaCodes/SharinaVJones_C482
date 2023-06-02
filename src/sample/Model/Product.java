package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the Product model.
 *
 * @author Sharina V. Jones
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Class constructor that creates and instance of this object
     * @param id the id
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the amount of the product in stock
     * @param min the minimum amount of the product allowed
     * @param max the maximum amount of the product allowed
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets the value of the id variable
     * @return id the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id variable
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the value of the name variable
     * @return name the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name variable
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of the price variable
     * @return price the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price variable
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the value of the stock variable
     * @return stock the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the value of the stock variable
     * @param stock the id
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the value of the min variable
     * @return min the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the value of the min variable
     * @param min the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the value of the max variable
     * @return max the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the value of the max variable
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds and associated part to the product
     * @param part the part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes and associated part from the product
     * @param selectedAssociatedPart the part to be added
     * @return returns true or false depending on if the part was deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        //if the part exists
        if(associatedParts.contains(selectedAssociatedPart)) {
            //remove it
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets all of the associated parts of a product
     * @return associatedParts a list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
