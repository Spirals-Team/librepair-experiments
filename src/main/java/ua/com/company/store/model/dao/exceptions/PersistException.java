package ua.com.company.store.model.dao.exceptions;

/**
 * Created by Владислав on 22.11.2017.
 */
public class PersistException extends Exception {
    private String massege;

    public PersistException(String message) {
        this.massege = massege;
    }

    @Override
    public String getMessage() {
        return massege;
    }
}
