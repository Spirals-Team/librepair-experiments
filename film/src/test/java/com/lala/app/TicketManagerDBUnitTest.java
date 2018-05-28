package com.lala.app;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.lala.app.domain.Movie;
import com.lala.app.domain.Ticket;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import com.lala.app.repository.TicketManager;;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
//@Commit
@Transactional(transactionManager = "txManager")
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class TicketManagerDBUnitTest {


	@Autowired
	TicketManager ticketManager;

	@Test
	@DatabaseSetup("/fullData.xml")
	@ExpectedDatabase(value = "/addMovieData.xml", 
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void getMovieCheck() throws Exception {
	    assertEquals(2, ticketManager.getAllMovies().size());
        
        Movie m = new Movie();
        m.setTitle("Titanic");
        m.setYear(2002);
        m.setGenre("Dramat");
        m.setDirector("Alan");        

        ticketManager.addMovie(m);
        assertEquals(3, ticketManager.getAllMovies().size());

    }
}