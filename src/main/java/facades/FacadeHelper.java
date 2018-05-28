package facades;

import interfaces.IBookFacadeHelper;

/**
 * This class is responsible for checking valid inputs for city, title and author
 */

public class FacadeHelper implements IBookFacadeHelper {
    public boolean checkValidCityInput(String city)  {


        if(isNumeric(city)) {
            return false;
        }
        if(city == null||city.equals("null") || city.trim().isEmpty()){
            return false;
        }
        if(city.length() > 500){
            return false;
        }
        return true;
    }
    public boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    
    public boolean checkValidBookTitleInput(String bookTitle)  {

        if(bookTitle == null || bookTitle == ""){
            return false;
        }
        if(bookTitle.length() > 500){
            return false;
        }
        return true;
    }
}
