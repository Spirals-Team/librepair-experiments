/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;
import interfaces.IBook;
import interfaces.ICity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Cherry Rose Semeña
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements IBook{
    String id;
    String bookTitle;
    Author author;
    List<ICity> cities;

    public Book(){

    }
    public Book(String title, Author author){
        this.bookTitle = title;
        this.author = author;
    }
    public Book(String title, Author author, List<ICity> cities){
        this.bookTitle = title;
        this.author = author;
        this.cities = cities;

    }
    public String getTitle() {
        return bookTitle;
    }

    public void setTitle(String title) {
        this.bookTitle = title;
    }

   

    public List<ICity> getCities() {
        return cities;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setCities(List<ICity> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", cities=" + cities +
                '}';
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    
}
