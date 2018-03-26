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

public Movie getById(int id) throws SQLException;

public Movie getByTitle(String title);

public List<Movie> getAll();

public int addMovie(Movie movie);

public void deleteMovie(Movie movie) throws SQLException;

public int updateMovie(int prevMovieId, Movie newMovie) throws SQLException;

public void dropDatatable() throws SQLException;


}

