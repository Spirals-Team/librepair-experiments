package interfaces;

import httpErrors.InvalidInputExceptionMapper;
import httpErrors.NotFoundExceptionMapper;
import java.util.List;

public interface ICityFacade {

    List<ICity> getCitiesByBookTitle (String bookTitle) throws NotFoundExceptionMapper, InvalidInputExceptionMapper;

}
