public class Product implements IDedObject{
    // Private class variables
    private int productID;
    private String productName;
    private String supplierName;

    // Constructor (only one is needed, as a Product object contains a product ID, product name, and the supplier's name).
    Product(int newProductID, String newProductName, String newSupplierName){
        productID = newProductID;
        productName = newProductName;
        supplierName = newSupplierName;
    }

    // Methods being implemented from the IDedObject interface.
    public int getID(){
        return productID;
    }

    public void printID(){
        System.out.println("\nProduct ID: " + productID + "\nProduct Name: " + productName + "\nSupplier Name: " + supplierName);
    }

    // Additional getter methods
    public String getProductName(){
        return productName;
    }

    public String getSupplierName(){
        return supplierName;
    }

    // Additional setter methods.
    public void setSupplierName(String newSupplierName){
        supplierName = newSupplierName;
    }

    public void setProductName(String newProductName){
        productName = newProductName;
    }

    public void setProductID(int newProductID){
        productID = newProductID;
    }
}
