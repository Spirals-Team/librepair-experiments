package com;

        import org.junit.Before;
        import org.junit.Rule;
        import org.junit.Test;
        import com.undeadcrud.domain.Undead;
        import com.undeadcrud.repository.UndeadRepository;
        import com.undeadcrud.repository.UndeadRepositoryFactory;
        import org.junit.rules.ExpectedException;
        import org.junit.runner.RunWith;
        import org.junit.runners.JUnit4;

        import static org.junit.Assert.*;

    @RunWith(JUnit4.class)
    public class UndeadTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public UndeadRepository undeadRepository;

    @Before
    public void initRepository(){
        undeadRepository = UndeadRepositoryFactory.getInstance();
        Undead szwedacz = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
        Undead ghoul = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
        Undead ozywieniec = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
        Undead lisz = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");

        szwedacz.setId(1);
        szwedacz.setTyp("Szwedacz");

        ghoul.setId(2);
        ghoul.setTyp("Ghoul");

        ozywieniec.setId(3);
        ozywieniec.setTyp("Ozywieniec");

        lisz.setId(4);
        lisz.setTyp("Lisz");

        undeadRepository.addUndead(szwedacz);
        undeadRepository.addUndead(ghoul);
        undeadRepository.addUndead(ozywieniec);
        undeadRepository.addUndead(lisz);
    }

//    @Test
//    public void getById()  throws Exception {
//
//        Undead zombie = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
//        zombie.setId(10);
//        zombie.setTyp("Zgnilec");
//        undeadRepository.addUndead(zombie);
//        assertNotNull(undeadRepository.getById(zombie.getId()));
//    }
//
//    @Test
//    public void addUndead() throws Exception {
//        Undead zombie = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
//        zombie.setId(10);
//        zombie.setTyp("Zgnilec");
//        undeadRepository.addUndead(zombie);
//        assertNotNull(undeadRepository.getById(zombie.getId()));
//        undeadRepository.deleteUndead(zombie);
//
//    }
//
//    @Test
//    public void deleteUndead()  throws Exception {
//        Undead zombie = undeadRepository.getById(1);
//        undeadRepository.deleteUndead(zombie);
//        if (undeadRepository.getAll().size() > 0){
//            assertNotNull(undeadRepository.getAll());
//        }else {
//            assertNull(undeadRepository.getById(zombie.getId()));
//        }
//    }
//
//    @Test
//    public void updateUndead ()  throws Exception {
//        Undead wywloka = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
//        wywloka.setTyp("wywloka");
//        int zombieToUpdate = 1;
//        undeadRepository.updateUndead(1, wywloka);
//        assertEquals(1,undeadRepository.getById(zombieToUpdate).getId());
//
//        for(Undead undead : undeadRepository.getAll()){
//            if(wywloka.getId() == zombieToUpdate) {
//                //if(wywloka.getId().equals((long)zombieToUpdate)) {
//                assertNotEquals(undead.getTyp(), wywloka.getTyp());
//            }
//        }
//    }
//
//    @Test
//    public void getAll() throws Exception {
//        assertNotNull(undeadRepository.getAll());
//    }
//
//
}