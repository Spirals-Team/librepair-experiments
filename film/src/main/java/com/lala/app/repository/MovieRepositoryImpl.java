package com.lala.app.repository;

import com.lala.app.domain.Movie;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRepositoryImpl implements MovieRepository {

    private Connection connection;
    private PreparedStatement addMovieStmt;
    private PreparedStatement updateMovieStmt;
    private PreparedStatement deleteMovieStmt;
    private PreparedStatement getAllMoviesStmt;
    private PreparedStatement getMovieByIdStmt;
    private PreparedStatement deleteTableStmt;
    private PreparedStatement getByTitleStmt;

    public MovieRepositoryImpl(Connection connection) throws SQLException {
        this.connection = connection;
        if(!isDatabaseReady()) {

		 createTables();
	 }
	 setConnection(connection);
}

    public boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("Movie".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int addMovie(Movie movie) {
        int count = 0;
        try {
            addMovieStmt.setString(1, movie.getTitle());
            addMovieStmt.setInt(2, movie.getYear());
            addMovieStmt.setString(3, movie.getGenre());
            addMovieStmt.setString(4, movie.getDirector());
            count =  addMovieStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }
    
	@Override
	public void deleteMovie(Movie movie) throws SQLException{
       
        
            deleteMovieStmt.setInt(1, movie.getId());
             deleteMovieStmt.executeUpdate();
        
        
	}

	@Override
	public Movie getById(int id) throws SQLException{
        Movie movie = new Movie();        
        try {
            getMovieByIdStmt.setInt(1, id);
            ResultSet rs = getMovieByIdStmt.executeQuery();

            while (rs.next()) {
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setDirector(rs.getString("director"));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return movie;
	}

    @Override
	public Movie getByTitle(String title) {
		Movie movie = new Movie();
		try
		{
			getByTitleStmt = connection.prepareStatement("SELECT * FROM Movie WHERE title = ?" + title + "'");
			ResultSet rs = getByTitleStmt.executeQuery();

			while(rs.next())
			{
				movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setDirector(rs.getString("director"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return movie;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = new LinkedList<>();
        try {
            ResultSet rs = getAllMoviesStmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setDirector(rs.getString("director"));
                movies.add(movie);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return movies;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addMovieStmt = connection.prepareStatement("INSERT INTO Movie (title, year, genre, director) VALUES (?, ?, ?, ?)");
        deleteMovieStmt = connection.prepareStatement("DELETE FROM Movie WHERE id = ?");
        getAllMoviesStmt = connection.prepareStatement("SELECT id, title, year, genre, director FROM Movie");
        getMovieByIdStmt = connection.prepareStatement("SELECT id, title, year, genre, director FROM Movie WHERE id = ?");
        updateMovieStmt = connection.prepareStatement("UPDATE Movie SET title = ?, year = ?, genre = ?, director = ? WHERE id = ?");
        deleteTableStmt = connection.prepareStatement("DROP TABLE Movie");
    }

    @Override
	public void createTables() throws SQLException
{
        connection.createStatement().executeUpdate(
            "CREATE TABLE Movie(id bigint GENERATED BY DEFAULT AS IDENTITY, title varchar(35) NOT NULL, year integer NOT NULL, genre varchar(35) NOT NULL, director varchar(35) NOT NULL)");
	}

	@Override
	public int updateMovie(int prevMovieId, Movie newMovie) throws SQLException {
        int count = 0;
        try {
            updateMovieStmt.setInt(5, prevMovieId);
            updateMovieStmt.setString(1, newMovie.getTitle());
            updateMovieStmt.setInt(2, newMovie.getYear());
            updateMovieStmt.setString(3, newMovie.getGenre());
            updateMovieStmt.setString(4, newMovie.getDirector());
            
            count = updateMovieStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
       return count;
	}

    @Override
    public void dropDatatable() throws SQLException{
        deleteTableStmt.executeUpdate();
    }   
  
      @Override
        public String introduceYourself() throws SQLException {
        
        return "About me";
    }
  


}