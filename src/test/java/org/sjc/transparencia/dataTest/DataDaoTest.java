package org.sjc.transparencia.dataTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sjc.transparencia.data.Data;
import org.sjc.transparencia.data.DataDao;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataDaoTest {

    private DataDao dataDao;
    private UUID dataUuid = UUID.randomUUID();
    private Data data;

    @Before
    public void setUp() {
        this.dataDao = new DataDao();
        this.data = new Data();
        data.setData_uuid(dataUuid);
        data.setMes(01);
        data.setAno(2018);
    }

    @Test
    public void insereDatas() {
        assertTrue(this.dataDao.insertData(this.data) != null);
    }

    @Test
    public void pagaTodosDados() {
        assertTrue(this.dataDao.retrieveAllData() != null);
    }

    @Test
    public void pagaTodosDadosPorAno() {
        assertTrue(this.dataDao.retrieveByYear(2018) != null);
    }

    @Test
    public void pegaDadosPorUuid() {
        this.dataDao.insertData(this.data);
        Data data = this.dataDao.retrieveByUuid(this.data.getData_uuid());
        assertEquals(this.data.getMes(), data.getMes());
        assertEquals(this.data.getAno(), data.getAno());
    }

    @Test
    public void naoPegaPorUuidInexistentes() {
        assertTrue(this.dataDao.retrieveByUuid(UUID.randomUUID()) == null);
    }

    @Test
    public void pegaUuidDaData() {
        assertTrue(this.dataDao.retrieveDataUuid(data) != null);
    }

    @Test
    public void apagaDatas() {
        this.dataDao.insertData(this.data);
        assertTrue(this.dataDao.removeData(this.data.getData_uuid()));
    }
}
