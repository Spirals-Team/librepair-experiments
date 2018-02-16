package pl.sternik.ss.zadania.Sklep;

import pl.sternik.ss.zadania.Sklep.book.Book;
import pl.sternik.ss.zadania.Sklep.order.Order;

public class Main {

    public static void main(String[] args) {
        //zad13
        Article article = new Article();
        article.setId(1);
        article.setName("one");
        article.setPrice(30);
        article.setDescription("desc");
        System.out.println(article.toString());

        //zad14
        Article article1 = new Article(2, "two", 20, "desc");
        Article article2 = new Article();
        System.out.println(Article.counter);

        //zad15
        Order order = new Order();
        //zad16
        Article article3 = new Article();
        article3.setId(4);
        article3.setName("name1");

        //zad17
        System.out.println(article3.toString());
        Book book = new Book();
        System.out.println(book.getAutor() + "\t" + book.getId());
        System.out.println(book.toString());
        //zad18
        Book book1 = new Book(1,"name",42, "desc", "autor", 2010);
        Article article4 = new Book();
        Article article5 = book1;
        article5.toString();
        //zad19

        //zad20

        //zad22


    }

}
