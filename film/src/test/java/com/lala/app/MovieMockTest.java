package com.lala.app;

import com.lala.app.repository.MovieRepository;
import com.lala.app.repository.MovieRepositoryFactory;
import com.lala.app.repository.MovieRepositoryImpl;
import com.lala.app.domain.Movie;

import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MovieMockTest {
    MovieRepository movieRepository;

    @Mock
    Connection connectionMock;
    @Mock
    PreparedStatement addMovieStmt;
    @Mock
    PreparedStatement updateMovieStmt;
    @Mock
    PreparedStatement deleteMovieStmt;
    @Mock
    PreparedStatement getAllMoviesStmt;
    @Mock
    PreparedStatement getMovieByIdStmt;
    @Mock
    PreparedStatement dropTableStmt;
    @Mock
    ResultSet resultSet;

    @Before
    public void initRepository() throws SQLException {
        when(connectionMock.prepareStatement("INSERT INTO Movie (title, year, genre, director) VALUES (?, ?, ?, ?)"))
                .thenReturn(addMovieStmt);
        when(connectionMock.prepareStatement("SELECT id, title, year, genre, director FROM Movie"))
                .thenReturn(getAllMoviesStmt);
        when(connectionMock.prepareStatement("SELECT id, title, year, genre, director FROM Movie WHERE id = ?"))
                .thenReturn(getMovieByIdStmt);
        when(connectionMock
                .prepareStatement("UPDATE Movie SET title = ?, year = ?, genre = ?, director = ? WHERE id = ?"))
                        .thenReturn(updateMovieStmt);
        when(connectionMock.prepareStatement("DELETE FROM Movie WHERE id = ?")).thenReturn(deleteMovieStmt);
        
        movieRepository.setConnection(connectionMock);
        verify(connectionMock).prepareStatement("INSERT INTO Movie (title, year, genre, director) VALUES (?, ?, ?, ?)");
        verify(connectionMock).prepareStatement("SELECT id, title, year, genre, director FROM Movie");
        verify(connectionMock).prepareStatement("SELECT id, title, year, genre, director FROM Movie WHERE id = ?");
        verify(connectionMock)
                .prepareStatement("UPDATE Movie SET title = ?, year = ?, genre = ?, director = ? WHERE id = ?");
        verify(connectionMock).prepareStatement("DELETE FROM Movie WHERE id = ?");
    }

    @Test
    public void checkAdding() throws Exception {
        when(addMovieStmt.executeUpdate()).thenReturn(1);

        Movie movie = new Movie();
        movie.setId((long)1);
        movie.setTitle("Titanic");
        movie.setYear(1997);
        movie.setGenre("Katastroficzny");
        movie.setDirector("James Cameron");

        assertEquals(1, movieRepository.addMovie(movie));
        verify(addMovieStmt, times(1)).setString(1, "Titanic");
        verify(addMovieStmt, times(1)).setInt(2, 1997);
        verify(addMovieStmt, times(1)).setString(3, "Katastroficzny");
        verify(addMovieStmt, times(1)).setString(4, "James Cameron");
        verify(addMovieStmt).executeUpdate();
    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            switch (columnLabel) {
            case "title":
                return "Titanic";
            case "genre":
                return "Katastroficzny";
            default:
                return "";
            }

        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    @Test
    public void checkGetting() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("title")).thenCallRealMethod();
        when(mockedResultSet.getInt("year")).thenCallRealMethod();
        when(mockedResultSet.getString("genre")).thenCallRealMethod();
        when(mockedResultSet.getString("director")).thenCallRealMethod();
        when(getAllMoviesStmt.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, movieRepository.getAll().size());
        verify(getAllMoviesStmt, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("title");
        verify(mockedResultSet, times(1)).getInt("year");
        verify(mockedResultSet, times(1)).getString("genre");
        verify(mockedResultSet, times(1)).getString("director");
        verify(mockedResultSet, times(2)).next();
    }

    @Test(expected = IllegalStateException.class)
    public void checkExceptionWhenAddingNullAdding() throws Exception {
        when(addMovieStmt.executeUpdate()).thenThrow(new SQLException());
        Movie movie = new Movie();
        movie.setTitle(null);
        movie.setYear(2010);
        movie.setGenre("Surrealistyczny");
        movie.setDirector("Christopher Nolan");

        assertEquals(1, movieRepository.addMovie(movie));
    }

    @Test
    public void checkDeleting() throws Exception {
        when(deleteMovieStmt.executeUpdate()).thenReturn(1);
        Movie movie = new Movie();
        movie.setId((long)1);
        movie.setTitle("Incepcja");
        movie.setYear(2010);
        movie.setGenre("Surrealistyczny");
        movie.setDirector("Christopher Nolan");

        assertEquals(1, movieRepository.deleteMovie(movie.getId()));
        verify(deleteMovieStmt, times(1)).setLong(1, movie.getId());
        verify(deleteMovieStmt).executeUpdate();
    }

    @Test
    public void checkUpdate() throws Exception {
        when(updateMovieStmt.executeUpdate()).thenReturn(1);
        Movie movie = new Movie();
        movie.setId((long)1);
        movie.setTitle("Incepcja");
        movie.setYear(2010);
        movie.setGenre("Surrealistyczny");
        movie.setDirector("Christopher Nolan");

        Movie newMovie = new Movie();
        newMovie.setId(movie.getId());
        newMovie.setTitle("Siedem");
        newMovie.setYear(1995);
        newMovie.setGenre("Thriller");
        newMovie.setDirector("David Fincher");

        assertEquals(1, movieRepository.updateMovie(movie.getId(), newMovie));
        verify(updateMovieStmt).executeUpdate();
    }
}