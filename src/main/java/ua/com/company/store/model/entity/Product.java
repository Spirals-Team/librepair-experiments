package ua.com.company.store.model.entity;

/**
 * Created by Владислав on 17.11.2017.
 */
public class Product extends Entity {
    private String title;
    private String description;
    private int price;
    private int imageId;

    private Product(ProductBuilder productBuilder) {
        super(productBuilder.getId());
        this.title = productBuilder.getTitle();
        this.description = productBuilder.getDescription();
        this.price = productBuilder.getPrice();
        this.imageId = productBuilder.getImageId();
    }

    public static class ProductBuilder {
        private int id;
        private String title;
        private String description;
        private int price;
        private int imageId;

        public ProductBuilder setId (final int id){
            this.id = id;
            return this;
        }

        public ProductBuilder setTitle (final String title){
            this.title = title;
            return this;
        }

        public ProductBuilder setDescr (final String description){
            this.description = description;
            return this;
        }

        public ProductBuilder setPrice(final int price){
            this.price = price;
            return this;
        }

        public ProductBuilder setImgId (final int imageId){
            this.imageId = imageId;
            return this;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getPrice() {
            return price;
        }

        public int getImageId() {
            return imageId;
        }

        public Product build(){
            return new Product(this);
        }

    }

    public Product() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageId=" + imageId +
                '}';
    }
}
