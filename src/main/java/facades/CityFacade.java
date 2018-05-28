package facades;

import httpErrors.InvalidInputExceptionMapper;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IDataAccessFactory;
import interfaces.ICity;
import java.util.ArrayList;
import java.util.List;
import interfaces.ICityFacade;

/**
 * This handles the logic for the object City
 */
public class CityFacade implements ICityFacade {

    /**
     * todo change to Factory Pattern
     */
    private IDataAccessFactory dataAccessFactory;
    private String database;
    private FacadeHelper helper = new FacadeHelper();

    public CityFacade(IDataAccessFactory dataAccessFactory, String database) {
        this.dataAccessFactory = dataAccessFactory;
        this.database = database;
    }

    public List<ICity> getCitiesByBookTitle(String bookTitle) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        //Remember to add the valid input checker
        bookTitle = bookTitle.trim();
        if (!helper.checkValidBookTitleInput(bookTitle)) {
            throw new InvalidInputExceptionMapper("Invalid Input");
        }

        try {
            List<ICity> cities = new ArrayList<ICity>();
            if (bookTitle != null && bookTitle.length() > 0) {
                cities = dataAccessFactory.getDataAccessor(this.database).getCitiesByBookTitle(bookTitle);
                if (cities.isEmpty()) {
                    throw new NotFoundExceptionMapper("No Cities Found");
                }
                return cities;
            } else {
                throw new NotFoundExceptionMapper("Invalid Input");
            }
        } catch (NotFoundExceptionMapper e) {
            throw e; //possible error from the DBAccessor - No Book Found
        }
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
