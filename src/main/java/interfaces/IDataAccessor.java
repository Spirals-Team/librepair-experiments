package interfaces;

import httpErrors.NotFoundExceptionMapper;
import java.util.List;

public interface IDataAccessor {
    public List<IBook> getBooksByCityName(String cityName) throws NotFoundExceptionMapper; //User Story # 1
    public List<ICity> getCitiesByBookTitle(String bookTitle) throws NotFoundExceptionMapper ; //User Story # 2
    public List<IBook> getBooksByAuthorName(String authorName) throws NotFoundExceptionMapper; //User Story # 3
    public String getName();
}
