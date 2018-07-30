package com.psg.glms.controller;

import java.util.List;

import com.psg.glms.model.Book;
import com.psg.glms.model.BookCategory;

public class BookCategoryBuilder {

    private BookCategory bookCategory = new BookCategory();

    public BookCategoryBuilder id(Long id) {
        bookCategory.setBookcatId(id);
        return this;
    }

    public BookCategoryBuilder name(String name) {
        bookCategory.setName(name);
        return this;
    }

    public BookCategoryBuilder book(List<Book> book) {
        bookCategory.setBook(book);
        return this;
    }

    public BookCategory build() {
        return bookCategory;
    }
}