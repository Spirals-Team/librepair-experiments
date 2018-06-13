package com.conveyal.gtfs.loader;

import com.conveyal.gtfs.error.NewGTFSErrorType;
import com.conveyal.gtfs.error.SQLErrorStorage;
import com.conveyal.gtfs.storage.StorageException;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLType;

public class IntegerField extends Field {

    private int minValue;

    private int maxValue;

    public IntegerField(String name, Requirement required) {
        this(name, required, 0, Integer.MAX_VALUE);
    }

    public IntegerField(String name, Requirement requirement, int maxValue) {
        this(name, requirement, 0, maxValue);
    }

    public IntegerField(String name, Requirement requirement, int minValue, int maxValue) {
        super(name, requirement);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private int validate (String string) {
        int i;
        try {
            i = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new StorageException(NewGTFSErrorType.NUMBER_PARSING, string);
        }
        if (i < minValue) throw new StorageException(NewGTFSErrorType.NUMBER_TOO_SMALL, string);
        if (i > maxValue) throw new StorageException(NewGTFSErrorType.NUMBER_TOO_LARGE, string);
        return i;
    }

    @Override
    public void setParameter (PreparedStatement preparedStatement, int oneBasedIndex, String string) {
        try {
            preparedStatement.setInt(oneBasedIndex, validate(string));
        } catch (Exception ex) {
            throw new StorageException(ex);
        }
    }

    @Override
    public String validateAndConvert (String string) {
        validate(string);
        return string;
    }

    @Override
    public SQLType getSqlType () {
        return JDBCType.INTEGER;
    }

}
