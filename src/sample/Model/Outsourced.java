package sample.Model;

/**
 * This class creates the Outsourced Part model
 *
 * @author Sharina V. Jones
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Class constructor that creates and instance of this object
     * @param id the id
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the amount of the product in stock
     * @param min the minimum amount of the product allowed
     * @param max the maximum amount of the product allowed
     * @param companyName the name of the company
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    /**
     * Sets the value for the companyName variable
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the value of the companyName variable
     * @return companyNmae
     */
    public String getCompanyName() {
        return companyName;
    }
}
