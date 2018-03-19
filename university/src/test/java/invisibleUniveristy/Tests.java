package invisibleUniveristy;

import invisibleUniveristy.crud.CreatorRepositoryFactory;
import invisibleUniveristy.crud.ICreatorRepository;
import invisibleUniveristy.invention.Creator;
import invisibleUniveristy.invention.Invention;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Tests {

    ICreatorRepository creatorRepository;

    @Test
    public void inventionFindTest(){
        ArrayList<Invention> inventionList = new ArrayList<>();

        inventionList.add(new Invention("Death claws", "Claws filled up with death", true));
        inventionList.add(new Invention("Puppy boots", "Boots shaped up like puppies paws", false));
        inventionList.add(new Invention("Gamepad", "Electronic device which is used to play games", false));

        InventionFinder inventionFinder = new InventionFinder(inventionList);
        Integer result = inventionFinder.addAllNonLethalInventions();

        assertNotNull(result);
        assertEquals(result, new Integer(2));
    }

    @Test
    public void insertTest(){
        Creator creator = new Creator();
        creator.setId(6L);
        creator.setName("Stefan");
        creator.setSurname("Krotkowski");

        creatorRepository.add(creator);
        assertNotNull(creatorRepository.getCreatorById(creator.getId()));
    }

    @Test
    public void updateTest(){
        Creator creatorToUpdate = creatorRepository.getCreatorById(2L);

        creatorToUpdate.setName("Juanita");
        creatorToUpdate.setSurname("Malinowska");
        creatorRepository.updateById(creatorToUpdate);

        assertEquals(creatorRepository.getCreatorById(2L).getSurname(), creatorToUpdate.getSurname());
        assertThat(creatorToUpdate.getSurname(), is("Malinowska"));
        assertThat(creatorToUpdate.getName(), is("Juanita"));

        Creator creator = creatorRepository.getCreatorById(3L);
        assertThat(creator.getSurname(), not("Malinowska"));
    }

    @Test
    public void findByIdTest(){
        Creator creator = creatorRepository.getCreatorById(0L);

        assertNotNull(creator);
        assertEquals("Jan Miętki", creator.getName() + " " + creator.getSurname());
    }

    @Test
    public void getAllTest(){
        List<Creator> allCreators = creatorRepository.getAllCreators();

        assertNotNull(allCreators);
        Creator creator = allCreators.get(3);
        assertNotNull(creator);

        try{
            Creator creatorToCatch = allCreators.get(0);
        } catch (IndexOutOfBoundsException anIndexOutOfBoundsException){
            assertThat(anIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
        }
    }

    @Test
    public void deleteByIdTest(){
        creatorRepository.deleteById(1L);

        List<Creator> creators = creatorRepository.getAllCreators();

        assertNull(creatorRepository.getCreatorById(1L).getSurname());
        assertNotNull(creatorRepository.getCreatorById(2L).getSurname());
        assertEquals(true, !creators.isEmpty());
    }

    @Before
    public void initRepository(){
        creatorRepository = CreatorRepositoryFactory.getInstance();

        Creator firstCreator = new Creator(1L, "Jan", "Miętki");
        Creator secondCreator = new Creator(2L, "Dominika", "Parawska");
        Creator thirdCreator = new Creator(3L, "Karol", "Sulżyński");
        Creator fourthCreator = new Creator(4L, "Karolina", "Więczkowska");
        Creator fifthCreator = new Creator(5L, "Justyna", "Jakalska");

        creatorRepository.add(firstCreator);
        creatorRepository.add(secondCreator);
        creatorRepository.add(thirdCreator);
        creatorRepository.add(fourthCreator);
        creatorRepository.add(fifthCreator);
    }

    @After
    public void dropRepository(){
        creatorRepository.dropTable();
    }
}
