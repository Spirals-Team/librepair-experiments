package ar.com.kfgodel.temas.acciones;

import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.orm.api.operations.TransactionOperation;
import ar.com.kfgodel.orm.api.operations.basic.Save;
import convention.persistent.Minuta;
import convention.persistent.Reunion;

/**
 * Created by sandro on 07/07/17.
 */
public class CrearMinuta implements TransactionOperation<Minuta> {

    private Reunion reunion;

    public static CrearMinuta create(Reunion reunion){
        CrearMinuta accion = new CrearMinuta();
        accion.reunion = reunion;
        return accion;
    }

    public static CrearMinuta create(){
        CrearMinuta accion = new CrearMinuta();
        return accion;
    }

    @Override
    public Minuta applyWithTransactionOn(TransactionContext transactionContext) {
        Minuta nuevaMinuta = Minuta.create(reunion);
        Save.create(nuevaMinuta).applyWithTransactionOn(transactionContext);
        return nuevaMinuta;
    }
}
