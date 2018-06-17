package com;

        import static org.junit.Assert.*;

        import com.undeadcrud.domain.Undead;
        import org.junit.Test;

public class UndeadRepositoryTest {
    @Test
    public void testCreateObject()
    {
        Undead undead = new Undead("Szkielet Wojownik","Szkielet", 5,"Cmentarz", "Demoralizacja");
        assertNotNull(undead);
    }
}