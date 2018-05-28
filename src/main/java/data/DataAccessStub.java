package data;

import entity.Author;
import interfaces.IDataAccessor;
import entity.Book;
import entity.City;
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
        String[] notfound = new String[]{"Lyngby", "America", "Europe"};

        for (int i = 0; i < notfound.length; i++) {
            if (cityName.equalsIgnoreCase(notfound[i])) {
                throw new NotFoundExceptionMapper("No Books Found");
            }
        }

        ICity city = new City("Manila", 14.6042, 120.9822);
        ICity city2 = new City("Stockholm", 59.33258000000001, 18.0649);
        ICity city3 = new City("Copenhagen", 55.675940000000004, 12.56553);
        List<ICity> list = new ArrayList();
        list.add(city);
        Author a = new Author("1", "Thompson, Norris");
        Author a2 = new Author("1", "United, Non");
        List<IBook> books = new ArrayList<IBook>();
        IBook book1 = new Book("Mother: A Story", a);
        IBook book2 = new Book("The 2000 CIA World Factbook", a2);
        IBook book3 = new Book("The 2000 CIA World Factbook", a2);

        books.add(book1);
        books.add(book2);
        books.add(book3);
        for (IBook book : books) {
            book.setCities(list);
        }

        //keep this for exception handler
        if (books.size()
                == 0) {
            throw new NotFoundExceptionMapper("No Books Found");
        }

        return books;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<ICity> getCitiesByBookTitle(String bookTitle) throws NotFoundExceptionMapper {
        String[] notfound = new String[]{"SecretBookEvar", "SecretBookEvar2", "SecretBookEvar3"};

        for (int i = 0; i < notfound.length; i++) {
            if (bookTitle.equalsIgnoreCase(notfound[i])) {
                throw new NotFoundExceptionMapper("No Cities Found");
            }
        }
        System.out.println("DataAccessStub_getCitiesByBookTitle()");
        List<ICity> list = new ArrayList();
        ICity city = new City("Manila", 14.6042, 120.9822);
        ICity city2 = new City("Stockholm", 59.33258000000001, 18.0649);
        ICity city3 = new City("Copenhagen", 55.675940000000004, 12.56553);

        if (bookTitle.equals("Mother: A Story")) {
            list.add(city);
        } else if (bookTitle.equals("The 2000 CIA World Factbook")) {
            list.add(city2);
            list.add(city3);
        }
        else{
            list.add(city);
            list.add(city2);
            list.add(city3);
        }

        return list; // not yet implemented
    }

    public List<IBook> getMentionedCitiesByAuthorName(String authorName) throws NotFoundExceptionMapper {
        String[] notFound = new String[]{"InvalidAuthorName", "InvalidAuthorName2", "InvalidAuthorName3"};
        if (Arrays.asList(notFound).contains(authorName)) {
            throw new NotFoundExceptionMapper("No Books Found");
        }
        Author a = new Author("1", "Norris, Kathleen Thompson");
        Author a2 = new Author("1", "United States. Central Intelligence Agency");
        List<IBook> books = new ArrayList<IBook>();
        IBook book1 = new Book("Mother: A Story", a);
        IBook book2 = new Book("The 2000 CIA World Factbook", a2);
        IBook book3 = new Book("The 2000 CIA World Factbook", a2);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        return books;
    }

    @Override
    public List<IBook> getBooksByGeolocation(double lat, double lon) throws NotFoundExceptionMapper {
        boolean notFound = false;
        if(lat==123 && lon == 123 ||  lat == 41 && lon == 12 || lat == 51 & lon == 11){
            notFound = true;
        }
                
        if (notFound) {
            throw new NotFoundExceptionMapper("No Books Found");
        }
        ICity city = new City("Manila", 14.6042, 120.9822);
        ICity city2 = new City("Stockholm", 59.33258000000001, 18.0649);
        ICity city3 = new City("Copenhagen", 55.675940000000004, 12.56553);
        List<ICity> list = new ArrayList();
        list.add(city);
        Author a = new Author("1", "Thompson_ Norris");
        Author a2 = new Author("1", "United_ Non");
        List<IBook> books = new ArrayList<IBook>();
        IBook book1 = new Book("Mother: A Story", a);
        IBook book2 = new Book("The 2000 CIA World Factbook", a2);
        IBook book3 = new Book("The 2000 CIA World Factbook", a2);

        books.add(book1);
        books.add(book2);
        books.add(book3);
        for (IBook book : books) {
            book.setCities(list);
        }
        return books;
    }
}
