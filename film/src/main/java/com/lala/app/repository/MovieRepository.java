package com.lala.app.repository;

import java.util.List;
import com.lala.app.domain.Movie;

import java.sql.Connection;
import java.sql.SQLException;

public interface MovieRepository {

String introduceYourself() throws SQLException;


public void createTables() throws SQLException;

public Connection getConnection();

public void setConnection(Connection connection) throws SQLException;

public Movie getById(long id) throws SQLException;

public Movie getByTitle(String title);

public Movie getByGenre(String genre);

public List<Movie> getAll();

public int addMovie(Movie movie);

public int deleteMovie(long id) throws SQLException;

public int updateMovie(long updateId, Movie newMovie);

public void dropDatatable() throws SQLException;

//public void initDatabase() throws SQLException;


}

