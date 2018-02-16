package pl.sternik.ss.zadania.Sklep.book;

import pl.sternik.ss.zadania.Sklep.Article;

public class Book extends Article {

    private String autor;
    private int year;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
        printChange();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        printChange();
    }

    public Book(){}

    public Book(int id, String name, double price, String description, String autor, int year) {
        super(id, name, price, description);
        this.autor = autor;
        this.year = year;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "Book{" +
                "autor='" + autor + '\'' +
                ", year=" + year +
                '}';
    }
}
