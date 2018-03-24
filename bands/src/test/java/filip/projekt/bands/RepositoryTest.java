package filip.projekt.bands;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
import filip.projekt.bands.bandCRUD.repository.BandRepositoryFactory;
public class RepositoryTest {
  BandRepository repo = null;
  
  Band motorhead ;
  Band metallica ;
  Band volbeat ;
  LinkedList<Band> l;
  

  
  
@BeforeClass
public static void init()
{
  
  BandRepository repo = BandRepositoryFactory.getInstance();

  repo.createTableBand();

  
}

  @Test
  public void checkInfo(){
    assertThat(repo.getInfo(), is("baza danych zespołów"));
  }
  @Test
  public void checkGetAll(){
    
    
	assertThat(repo.getAll(),is(l));
  
  
  }
 

@Test
  public void checkSelect(){
    assertThat(repo.selectFromBand(0),is(motorhead));
    assertThat(repo.selectFromBand(1),is(metallica));
    assertThat(repo.selectFromBand(2),is(volbeat));
    
  }
  @Test
  public void checkDelete(){
    l.remove(metallica);
    repo.deleteFromBand(1);
    assertThat(repo.selectFromBand(1),is(not(metallica)));
    assertThat(repo.selectFromBand(0),is(motorhead));
    assertThat(repo.selectFromBand(2),is(volbeat));  
  }
  @Test
  public void checkInsert(){
    Band pantera = new Band(3,"Pantera","Groove Metal",3);
    repo.insertToBand(pantera);
    assertThat(repo.selectFromBand(3),is(pantera));
    assertThat(repo.selectFromBand(0),is(motorhead));
    assertThat(repo.selectFromBand(2),is(volbeat));
  }
  @Test
  public void checkUpdate(){
    Band pantera = new Band(3,"Pantera","Groove Metal",3);
    Band def = new Band(2,"Def Leppard","Groove Metal",4);
    repo.updateById(2,def);
    assertThat(repo.selectFromBand(4),is(def));

    assertThat(repo.selectFromBand(3),is(pantera));
    assertThat(repo.selectFromBand(0),is(motorhead));
    assertThat(repo.selectFromBand(2),is(not(volbeat)));
  }
  @AfterClass
  public  static void checkDrop(){
    BandRepository repo = BandRepositoryFactory.getInstance();
        repo.dropTableBand();

        assertNull(repo.getAll());
        
      
     

  }

  public RepositoryTest(){
  Band motorhead = new Band(0,"Motorhead","Rock And Roll",3);
  Band metallica = new Band(1,"Metallica","Metal",4);
  Band volbeat = new Band(2,"Volbeat","Groove Metal",5);
  LinkedList<Band> l = new LinkedList<Band>();
 
  l.add(motorhead);
  l.add(metallica);
  l.add(volbeat);

  }

}
