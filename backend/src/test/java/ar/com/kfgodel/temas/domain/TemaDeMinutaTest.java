package ar.com.kfgodel.temas.domain;

import convention.persistent.TemaDeMinuta;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sandro on 13/07/17.
 */
public class TemaDeMinutaTest {

    @Test
    public void test01UnTemaDeMinutaPuedeSerMarcadoComoTratado(){
        TemaDeMinuta temaDeMinuta = new TemaDeMinuta();
        temaDeMinuta.setFueTratado(true);
        Assert.assertTrue(temaDeMinuta.getFueTratado());
    }

}
