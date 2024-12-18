package LapFarm.DTO;

public class ViewedItem {
    private int id;        // Unique identifier for the product
    private String name;    // Name of the product
    private String image;   // URL of the product image
    private long price;    // Price of the product

    private String encryptedId; 
    // Default constructor
    public ViewedItem() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long l) {
        this.price = l;
    }
    
    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }
}