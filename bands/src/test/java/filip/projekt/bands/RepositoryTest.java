package filip.projekt.bands;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;

import org.junit.Test;

import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
public class RepositoryTest {
  BandRepository repo = null;
  
  Band motorhead ;
  Band metallica ;
  Band volbeat ;
  LinkedList<Band> l;
  

  
  
@Test
public void init()
{
  
  

  repo.createTableBand();

  assertNotNull("utworzyło listę zespołów", l);
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
    repo.updateById(4,def);
    assertThat(repo.selectFromBand(4),is(def));

    assertThat(repo.selectFromBand(3),is(pantera));
    assertThat(repo.selectFromBand(0),is(motorhead));
    assertThat(repo.selectFromBand(2),is(not(volbeat)));
  }
  @Test
  public void checkDrop(){
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
