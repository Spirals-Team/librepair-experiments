package filip.projekt.bands;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNoException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
import filip.projekt.bands.bandCRUD.repository.BandRepositoryFactory;
public class RepositoryTest {

  public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

  BandRepository repo ;
  
  /*Band motorhead ;
  Band metallica ;
  Band volbeat ;*/
  LinkedList<Band> l;
  

  
  
@BeforeClass 
public static void init() throws SQLException
{
  
   
  

  
  
}

@Before
public void fill() throws SQLException{

  BandRepository repo = new BandRepositoryFactory(DriverManager.getConnection(url));
  repo.createTableBand();


  Random r = new Random();
  for (int i = 0; i<100; i++) {
    Band b = new Band( "Zespół" + r.nextInt(100),"Gatunek" + r.nextInt(100), r.nextInt(100));
    //System.out.println(b.getName());
    assertNotNull(repo);
    repo.add(b);
    assertNotNull(l);
    l.add(b);
    
  }
}
@After
public void clear() throws SQLException{
  for (Band b : l) {
   repo.deleteFromBand(b);
   
    
  }
  l.clear();

  repo.dropTableBand();
		

        //assertNull(repo.getAll());
}

  @Test
  public void checkInfo(){
    assertThat(repo.getInfo(), is("To jest program na potrzeby przedmiotu testowanie automatyczne"));
  }
  @Test
  public void checkGetAll(){
    
    
	assertEquals(repo.getAll(),l);
  
  
  }
 

@Test
  public void checkSelectGood() throws SQLException{
    Band b = l.get(5);
  
		assertEquals(repo.selectFromBand(b.getId()),b);
	
     b = l.get(10);
  
     assertEquals(repo.selectFromBand(b.getId()),b);
	
	
  }
  
  @Test

  public void checkSelectBad() throws SQLException{
    Band b = new Band();
  
		//assertEquals(repo.selectFromBand(b.getId()),b);
	
     b = l.get(10);
  
    // assertEquals(repo.selectFromBand(b.getId()),b);
	
	
  }
    
   
  
  @Test (expected = SQLException.class)
  public void checkDeleteGood() throws SQLException{
    Band b = l.get(5);
    l.remove(b);
    assertEquals(1, repo.deleteFromBand(b));
  
   // assertThat(repo.selectFromBand(b.getId()),is(not(b)));
    
    assertEquals( repo.getAll(), l);
    assertThat( repo.getAll(), equalTo(l));
    assertNull(repo.selectFromBand(b.getId()));
  }  
  

@Test(expected = SQLException.class)
  public void checkDeleteBad() throws SQLException{
   
    Band b = new Band();
    l.remove(b);
    assertEquals(1, repo.deleteFromBand(b));
  
    assertThat(repo.selectFromBand(b.getId()),is(not(b)));
    assertNull(repo.selectFromBand(b.getId()));
    assertEquals( repo.getAll(), l);
    assertThat( repo.getAll(), equalTo(l));
    
	}  




  
  @Test

  public void checkInsert() throws SQLException{
    Band pantera = new Band("Pantera","Groove Metal",3);
    assertEquals(1, repo.add(pantera));
    l.add(pantera);
    assertEquals(repo.getAll(), l);
  
		assertEquals(repo.selectFromBand(pantera.getId()),pantera);
	
	
	}
  
  @Test
  public void checkUpdateGood() throws SQLException{
    Band b = l.get(7);
    b.setName("Volbeat");
    b.setGenre("Rock and Roll");
    b.setNumberOfMembers(5);
    l.set(7,b);
    
    assertEquals(1, repo.updateBand(b));
    repo.updateBand(b);
    System.out.println(repo.selectFromBand(7).toString());
    assertEquals(repo.selectFromBand(b.getId()).getGenre(),"Rock and Roll");
    assertEquals(repo.getAll(), l);
  }


  @Test

  public void checkUpdateBad() throws SQLException{
    Band b = new Band();
    b.setId(7);
    b.setName("Volbeat");
    b.setGenre("Rock and Roll");
    b.setNumberOfMembers(5);
    l.set(7,b);
    System.out.println(repo.selectFromBand(7));
    
    assertEquals(1, repo.updateBand(b));
    assertThat(repo.selectFromBand(7).getGenre(),equalTo("Rock and Roll"));
    assertEquals(repo.getAll(), l);
  }


  @AfterClass
  public  static void checkDrop() throws SQLException{
    BandRepository repo = BandRepositoryFactory.getInstance();
      
			
        
      
     

  }

  


  public RepositoryTest() throws SQLException{
  repo = new BandRepositoryFactory(DriverManager.getConnection(url));
 /* Band motorhead = new Band(0,"Motorhead","Rock And Roll",3);
  Band metallica = new Band(1,"Metallica","Metal",4);
  Band volbeat = new Band(2,"Volbeat","Groove Metal",5);*/
   l = new LinkedList<Band>();
 
  /*l.add(motorhead);
  l.add(metallica);
  l.add(volbeat);*/

  }

}
