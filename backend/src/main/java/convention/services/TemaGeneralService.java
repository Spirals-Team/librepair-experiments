package convention.services;

import ar.com.kfgodel.orm.api.operations.basic.FindAll;
import ar.com.kfgodel.temas.filters.reuniones.ReunionesAbiertas;
import convention.persistent.Reunion;
import convention.persistent.TemaGeneral;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by sandro on 04/07/17.
 */
public class TemaGeneralService extends Service<TemaGeneral> {

    @Inject
    private TemaService temaService;

    public TemaGeneralService(){
        setClasePrincipal(TemaGeneral.class);
    }

    public List<TemaGeneral> getAll() {
        return getAll(FindAll.of(TemaGeneral.class));
    }

    @Override
    public TemaGeneral save(TemaGeneral temaGeneral){
        TemaGeneral temaGeneralGuardado = super.save(temaGeneral);
        ReunionService reunionService = appInjector.getImplementationFor(ReunionService.class).get();
        List<Reunion> reunionesAbiertas = reunionService.getAll(ReunionesAbiertas.create());
        reunionesAbiertas.forEach(reunion -> reunion.agregarTemaGeneral(temaGeneral));
        reunionService.updateAll(reunionesAbiertas);
        return temaGeneralGuardado;
    }

    @Override
    public void delete(Long id){
        temaService.deleteAllForTemaGeneral(id);
        super.delete(id);
    }

    @Override
    public TemaGeneral update(TemaGeneral newState){
        temaService.updateAllForTemaGeneral(newState);
        return super.update(newState);
    }
}
