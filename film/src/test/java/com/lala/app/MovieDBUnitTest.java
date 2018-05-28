
package com.lala.app;

import java.net.URL;
import java.sql.DriverManager;
import java.util.concurrent.ExecutionException;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.lala.app.domain.Movie;
import com.lala.app.repository.MovieRepository;
import com.lala.app.repository.MovieRepositoryImpl;

@Ignore
@RunWith(JUnit4.class)

public class MovieDBUnitTest extends DBTestCase
{

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    MovieRepository movieRepository;

    @After
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    @Before
    public void setUp() throws Exception
    {
        movieRepository = new MovieRepositoryImpl(DriverManager.getConnection(url));
        super.setUp();
        
    }

    @Test
    public void doNothing()
    {
        assertEquals(3,movieRepository.getAll().size());
    }
    @Test
    public void checkAdding() throws Exception
    {
        Movie movie = new Movie();
        movie.setTitle("Siedem dusz");
        movie.setYear(2007);
        movie.setGenre("Romans");
        movie.setDirector("Sam");

        assertEquals(1, movieRepository.addMovie(movie));

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("MOVIE");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] {"ID"});
        IDataSet expectedDataSet = getDataSet("ds-1.xml");
        ITable expectedTable = expectedDataSet.getTable("MOVIE");
        Assertion.assertEquals(expectedTable, filteredTable);
        
           
        long id = movieRepository.getByTitle("Siedem dusz").getId();
        movieRepository.deleteMovie(id);
    }

    @Test
     public void checkUpdating() throws Exception
     {
         Movie movie = movieRepository.getById(1);
         movie.setTitle("Coco");
         assertEquals(1, movieRepository.updateMovie(movie.getId(), movie));

         IDataSet dbDataSet = this.getConnection().createDataSet();
         ITable actuaTable = dbDataSet.getTable("MOVIE");
         ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actuaTable, new String[] {"ID"});
         IDataSet expectedDataSet = getDataSet("ds-2.xml");
         ITable expectedTable = expectedDataSet.getTable("MOVIE");
         Assertion.assertEquals(expectedTable, filteredTable);
     }

     @Test
     public void checkDeleting() throws Exception
     {
                  
         assertEquals(1, movieRepository.deleteMovie((long)2));
         IDataSet dbDataSet = this.getConnection().createDataSet();
         ITable actuaTable = dbDataSet.getTable("MOVIE");
         ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actuaTable, new String[] {"ID"});
         IDataSet expectedDataSet = getDataSet("ds-3.xml");
         ITable expectedTable = expectedDataSet.getTable("MOVIE");
         Assertion.assertEquals(expectedTable, filteredTable);
     }


     @Test
     public void checkGet() throws Exception
     {
         Movie movie = movieRepository.getById(1);
         assertEquals("Titanic", movie.getTitle());
     }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws ExecutionException
    {
        return DatabaseOperation.DELETE;
    }
	@Override
	protected IDataSet getDataSet() throws Exception {
		return this.getDataSet("ds-0.xml");
    }
    
    protected IDataSet getDataSet(String dataset) throws Exception
    {
        URL url = getClass().getClassLoader().getResource(dataset);
        FlatXmlDataSet ret = new FlatXmlDataSetBuilder().build(url.openStream());
        return ret;

    }

}

   