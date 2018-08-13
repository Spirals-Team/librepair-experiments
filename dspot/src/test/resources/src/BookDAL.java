package info.sanaulla.dal;
 
import info.sanaulla.models.Book;

import java.util.Collections;
import java.util.List;

/**
* API layer for persisting and retrieving the Book objects.
*/
public class BookDAL {
 
  private static info.sanaulla.dal.BookDAL bookDAL = new info.sanaulla.dal.BookDAL();
 
  public List<Book> getAllBooks(){
      return Collections.EMPTY_LIST;
  }
 
  public Book getBook(String isbn){
      return null;
  }
 
  public String addBook(Book book){
      return book.getIsbn();
  }
 
  public String updateBook(Book book){
      return book.getIsbn();
  }
 
  public static info.sanaulla.dal.BookDAL getInstance(){
      return bookDAL;
  }
}