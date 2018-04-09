package lelental;

import lelental.domain.Shelf;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Paweł Lelental
 **/
public class ShelfTest {

    private Shelf shelf;

    @Before
    public void build_shelf(){
        shelf = new Shelf();
    }

    @Test
    public void check_if_shelf_exists(){
        assertNotNull(shelf);
    }


}