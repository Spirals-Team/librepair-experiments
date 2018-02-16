package pl.sternik.ss.zadania.Sklep;

import org.apache.log4j.Logger;

public class Article {

    private int id;
    private String name;
    private double price;
    private String description;

    public static int counter;
    private static final Logger logger = Logger.getLogger("Article");

    public Article(){counter++;}

    public Article(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        counter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        printChange();
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected void printChange(){
        logger.debug("Zmieniono wartość pola");
       // System.out.println("Zmieniono wartość pola");

    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
