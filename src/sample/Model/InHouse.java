package sample.Model;

/**
 * Creates the InHouse Part model
 *
 * @author Sharina V. Jones
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Class constructor that creates and instance of this object
     * @param id the id
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the amount of the product in stock
     * @param min the minimum amount of the product allowed
     * @param max the maximum amount of the product allowed
     * @param machineId the machine id of the product
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the value for the machineId variable
     * @param machineId the machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the value of the machineId variable
     * @return machineId the machine id
     */
    public int getMachineId() {
        return machineId;
    }
}
