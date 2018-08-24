package convention.services;

import ar.com.kfgodel.orm.api.operations.basic.FindAll;
import convention.persistent.TemaDeMinuta;

import java.util.List;

/**
 * Created by fede on 07/07/17.
 */
public class TemaDeMinutaService extends Service<TemaDeMinuta> {

    public TemaDeMinutaService(){
        setClasePrincipal(TemaDeMinuta.class);
    }


    public List<TemaDeMinuta> getAll() {
        return getAll(FindAll.of(TemaDeMinuta.class));
    }
}
