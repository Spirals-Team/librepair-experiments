package convention.rest.api;

import ar.com.kfgodel.dependencies.api.DependencyInjector;
import convention.persistent.TemaDeMinuta;
import convention.rest.api.tos.TemaDeMinutaTo;
import convention.services.TemaDeMinutaService;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Created by fede on 07/07/17.
 */

@Produces("application/json")
@Consumes("application/json")
public class TemaDeMinutaResource{

        @Inject
    TemaDeMinutaService temaDeMinutaService;

        private ResourceHelper resourceHelper;
    @GET
    @Path("/{resourceId}")
    public TemaDeMinutaTo getSingle(@PathParam("resourceId") Long id) {
            TemaDeMinuta temaDeMinuta=temaDeMinutaService.get(id);
        return  getResourceHelper().convertir(temaDeMinuta, TemaDeMinutaTo.class);
    }
    @PUT
    @Path("/{resourceId}")
    public TemaDeMinutaTo update(TemaDeMinutaTo newState, @PathParam("resourceId") Long id) {
        TemaDeMinuta temaDeMinutaActualizada = temaDeMinutaService.update( getResourceHelper().convertir(newState, TemaDeMinuta.class));
        return  getResourceHelper().convertir(temaDeMinutaActualizada, TemaDeMinutaTo.class);
    }
    public static TemaDeMinutaResource create(DependencyInjector appInjector) {
        TemaDeMinutaResource temaDeMinutaResource = new TemaDeMinutaResource();
        temaDeMinutaResource.resourceHelper= ResourceHelper.create(appInjector);
        temaDeMinutaResource.temaDeMinutaService=appInjector.createInjected(TemaDeMinutaService.class);
        temaDeMinutaResource. getResourceHelper().bindAppInjectorTo(TemaDeMinutaResource.class, temaDeMinutaResource);
        return temaDeMinutaResource;
    }

    public ResourceHelper getResourceHelper() {
        return resourceHelper;
    }
}
