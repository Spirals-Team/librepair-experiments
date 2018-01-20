package ua.com.company.store.model.dto;

/**
 * Created by Владислав on 13.12.2017.
 */
public class ProductDto {
    private String title;
    private String description;
    private int price;
    private String[] imageInformation;

    public ProductDto(String title, String description, int price, String[] imageInformation) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageInformation = imageInformation;
    }

    public String[] getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(String[] imageInformation) {
        this.imageInformation = imageInformation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
