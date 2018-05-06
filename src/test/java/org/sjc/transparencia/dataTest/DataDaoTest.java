package org.sjc.transparencia.dataTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sjc.transparencia.data.Data;
import org.sjc.transparencia.data.DataDao;

import java.util.UUID;

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
        this.dataDao.insertData(this.data);
    }

    @After
    public void tearDown(){
        this.dataDao.removeData(data.getData_uuid());
    }

    @Test
    public void insereDatas(){
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
        assertTrue(this.dataDao.retrieveByUuid(this.data.getData_uuid()) != null);
    }

    @Test
    public void naoPegaPorUuidInexistentes(){
        assertTrue(this.dataDao.retrieveByUuid(UUID.randomUUID()) == null);
    }

    @Test
    public void pegaUuidDaData() {
        assertTrue(this.dataDao.retrieveDataUuid(data) != null);
    }

    @Test
    public void apagaDatas(){
        assertTrue(this.dataDao.removeData(data.getData_uuid()));
    }
}
