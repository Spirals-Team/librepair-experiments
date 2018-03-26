package com.lala.app;
import com.lala.app.repository.MovieRepository;
import com.lala.app.repository.MovieRepositoryFactory;
import com.lala.app.repository.MovieRepositoryImpl;
import com.lala.app.domain.Movie;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.rules.ExpectedException;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;


import java.sql.DriverManager;
import java.sql.SQLException;

public class MovieTest {
    
    MovieRepository movieRepository;

    @Before
    public void initDatabase() {
        
        movieRepository = MovieRepositoryFactory.getInstance();
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();              

        movie1.setId(1);
        movie1.setTitle("Titanic");
        movie1.setYear(1997);
        movie1.setGenre("Katastroficzny");
        movie1.setDirector("James Cameron");

        movie2.setId(2);
        movie2.setTitle("Nietykalni");
        movie2.setYear(2011);
        movie2.setGenre("Dramat");
        movie2.setDirector("Olivier Nakache");

        movieRepository.addMovie(movie1);
        movieRepository.addMovie(movie2); 
           
    }

    @Test
    public void getById() throws SQLException{
        int findId = 1;
        assertNotNull(movieRepository.getById(findId).getId());
    }

    @Test
    public void getAll() {
        assertNotNull(movieRepository.getAll());
    }

    @Test
    public void addMovie() throws SQLException{
        Movie movie = new Movie();
        movie.setId(4);
        movie.setTitle("Wyspa tajemnic");
        movie.setYear(2010);
        movie.setGenre("Dramat");
        movie.setDirector("Martin Scorsese");
        movieRepository.addMovie(movie);
        assertNotNull(movieRepository.getById(movie.getId()));
    }

    @Test
    public void updateMovie() throws SQLException{
       
        
        Movie movie5 = new Movie();
        
        movie5.setTitle("Zielona mila");
        movie5.setYear(1999);
        movie5.setGenre("Dramat");
        movie5.setDirector("Patric Ketch");
        int updateId = 5;
        movieRepository.updateMovie(updateId, movie5);
        
        for (Movie movie : movieRepository.getAll()) {
            if (movie5.getTitle().equals(movieRepository.getById(updateId).getTitle())) {
                if(updateId == movie.getId()){
                    assertEquals(movieRepository.getById(updateId).getTitle(), movie.getTitle());
                }
                else {
                    assertNotEquals(movieRepository.getById(updateId).getTitle(), movie.getTitle());
                }
            }    
        }    
    }

    @Test
    public void deleteMovie() throws SQLException{
        Movie movie = movieRepository.getById(1);
        movieRepository.deleteMovie(movie);

        assertNull(movieRepository.getById(1).getTitle());
        assertFalse(movieRepository.getAll().isEmpty());

    }     
   


    @Test
    public void introduceYourself() throws SQLException{
        assertNotNull(movieRepository.introduceYourself());
        System.out.println(movieRepository.introduceYourself());
    }


    @After
    public void dropTable() throws SQLException {
        movieRepository.dropDatatable();
    } 

}
