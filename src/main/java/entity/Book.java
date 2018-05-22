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
    String title;
    String author;
    List<ICity> cities;
    public Book(){

    }
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }
    public Book(String title, String author, List<ICity> cities){
        this.title = title;
        this.author = author;
        this.cities = cities;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", cities=" + cities +
                '}';
    }
    
}
