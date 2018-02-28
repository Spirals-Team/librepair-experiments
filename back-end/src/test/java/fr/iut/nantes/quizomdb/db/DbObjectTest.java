package fr.iut.nantes.quizomdb.db;

import org.junit.Before;

/**
 * @version 1.0
 * @see DbObject
 * @since 1.0
 */
public class DbObjectTest {

    private Idb db;

    /**
     * init db whit {@link DbObject}
     *
     * @throws Exception
     * @since 1.0
     */
    @Before
    public void setUp() throws Exception {
        db = new DbObject();
    }
}