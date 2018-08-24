package ar.com.kfgodel.temas.domain;

import convention.persistent.Minuta;
import convention.persistent.Reunion;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by sandro on 06/07/17.
 */
public class MinutaTest {

    @Test
    public void test01UnaMinutaNuevaNoTieneAsistentes(){
        Reunion reunion = Reunion.create(LocalDate.of(2017, 06, 26));
        Minuta minuta = Minuta.create(reunion);
        Assert.assertEquals(0, minuta.getAsistentes().size());
    }
}
