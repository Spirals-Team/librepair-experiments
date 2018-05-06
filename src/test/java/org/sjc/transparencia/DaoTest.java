package org.sjc.transparencia;


import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class DaoTest {

    @Test
    public void verificaConexao() {
        assertTrue(Dao.getConnection() != null);
    }

    @Test
    public void verificaSingleton() {
        assertSame(Dao.getConnection(), Dao.getConnection());

    }
}
