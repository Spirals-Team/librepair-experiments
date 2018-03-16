package pl.arek.undeadTests.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.arek.domain.Undead;
import pl.arek.repository.UndeadRepository;
import pl.arek.repository.UndeadRepositoryFactory;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UndeadTest {

    UndeadRepository undeadRepository;

    @Ignore
    @Test
    public void isZombie() {
        Undead zombie = new Undead();
        assertNotNull(zombie);
    }

    @Test
    public void getById() throws SQLException{
        int idToFind = 170;
        assertNotNull(undeadRepository.getById(idToFind));
    }

    @Test
    public void addUndead() throws SQLException{
        Undead zombie = new Undead();
        zombie.setType("Testowy 1");
        undeadRepository.addUndead(zombie);
        assertNotNull(undeadRepository.getById(170));

    }

    @Ignore
    @Test
    public void deleteUndead() throws SQLException{
        Undead zombie = undeadRepository.getById(1);
        undeadRepository.deleteUndead(zombie);

        if (undeadRepository.getAll().size() > 0) {
            assertNotNull(undeadRepository.getAll());
            assertNull(undeadRepository.getById(zombie.getId()));

        }
    }

    @Test
    public void updateUndead() throws SQLException{
        Undead clicker = new Undead();
        clicker.setType("Clicker");
        int zombieToUpdate = 170;
        undeadRepository.updateUndead(zombieToUpdate, clicker);
        assertEquals(undeadRepository.getById(zombieToUpdate).getType(), clicker.getType());

//        for (Undead undead : undeadRepository.getAll()) {
//            if (clicker.getId() == zombieToUpdate) {
//                assertNotEquals(undead.getType(), clicker.getType());
//            }
//        }
    }

    @Test
    public void getAll() {
        assertNotNull(undeadRepository.getAll());
    }

    @Before
    public void initRepository() {
        undeadRepository = UndeadRepositoryFactory.getInstance();
        Undead walker = new Undead();
        Undead ghoul = new Undead();
        Undead voodoo = new Undead();
        Undead screamer = new Undead();

        walker.setType("Walker");

        ghoul.setType("Ghoul");

        voodoo.setType("Voodoo");

        screamer.setType("Screamer");

        undeadRepository.addUndead(screamer);
        undeadRepository.addUndead(walker);
        undeadRepository.addUndead(ghoul);
        undeadRepository.addUndead(voodoo);
    }

//    @After
//    public void dropTable() throws SQLException {
//        undeadRepository.dropDatatable();
//        //   assertEquals(null, undeadRepository.getAll());
//    }



}
