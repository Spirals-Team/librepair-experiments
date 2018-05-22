package data;

import interfaces.IDataAccessor;
import entity.Book;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.ICity;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataAccessStub implements IDataAccessor {

    private String name = "DataAccessStub";

    @Override
    public List<IBook> getBooksByCityName(String cityName) throws NotFoundExceptionMapper {
        String[] notfound = new String[]{"Lyngby","America", "Europe"};
        for (int i = 0; i <notfound.length ; i++) {
            if(cityName.equalsIgnoreCase(notfound[i])){
                throw new NotFoundExceptionMapper("NotFound");
            }
        }

        List<IBook> books = new ArrayList<IBook>();
        IBook book1 = new Book("Autobiography of a Child", "Hannah Lynch");
        IBook book2 = new Book("Miles Standish", "John S. C. Abbott");
        IBook book3 = new Book("Stories of Symphonic Music", "Lawrence Gilman");
        IBook book4 = new Book("Captain Billy's Whiz Bang, Vol. 2, No. 21, June, 1921 ", "Various");
        IBook book5 = new Book("Rambles in Yucatan", "Benjamin Moore Norman");
        IBook book6 = new Book("Alice's Adventures in Wonderland", "Lewis Carroll");
        IBook book7 = new Book("Pride and Prejudice", "Jane Austen");
        if (cityName.length() > 5) {
            books.add(book1);
        } else {
            books.add(book1);
            books.add(book2);
            books.add(book3);
            books.add(book4);
            books.add(book5);
            books.add(book6);
            books.add(book7);
        }

        //keep this for exception handler
        if (books.size() == 0) {
            throw new NotFoundExceptionMapper("No Book Found");
        }

        return books;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<ICity> getCitiesByBookTitle(String bookTitle) {
        System.out.println("DataAccessStub_getCitiesByBookTitle()");
        List<ICity> list = new ArrayList();
        ICity city1 = new entity.City("New York", 40.730610, -73.935242);
        ICity city2 = new entity.City("Chicago", 41.881832, -87.623177);
        ICity city3 = new entity.City("London", 51.509865, -0.118092);
        list.add(city1);
        list.add(city2);
        list.add(city3);
        return list; // not yet implemented
    }

    public List<IBook> getBooksByAuthorName(String authorName) throws NotFoundExceptionMapper {
        String[] notFound = new String[]{"J.K. Rowling", "Oprah Winfrey", "Stieg Larsson"};
        if(Arrays.asList(notFound).contains(authorName)){
            throw new NotFoundExceptionMapper("Author not found");
        }

        List<IBook> books = new ArrayList<>();

        books.add(new Book("Autobiography of a Child", "Hannah Lynch"));
        books.add(new Book("Miles Standish", "John S. C. Abbott"));
        books.add(new Book("Stories of Symphonic Music", "Lawrence Gilman"));
        books.add(new Book("Captain Billy's Whiz Bang, Vol. 2, No. 21, June, 1921 ", "Various"));
        books.add(new Book("Rambles in Yucatan", "Benjamin Moore Norman"));
        books.add(new Book("Alice's Adventures in Wonderland", "Lewis Carroll"));
        books.add(new Book("Pride and Prejudice", "Jane Austen"));

        return books.subList(0, authorName.length() < 6 ? authorName.length() : 6);
    }
}
