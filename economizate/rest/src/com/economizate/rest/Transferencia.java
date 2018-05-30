package com.economizate.rest;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/transferencia")
public class Transferencia {
	
		@Path("{value}")
		@POST
		@Produces("application/json")
		public Response generarTransferencia(@PathParam("value") double monto ) {
			
			Logger.getLogger(Transferencia.class.getName()).info("******** Servicio para generar transferencia de"
					+ " monto: " + monto +  " ********");
			
			String request = "Se ha iniciado una transferencia por un monto de: " + monto;
			
			if(5 == monto) {
				JSONObject respuestaJson = new JSONObject().append("result", request);
				return Response.status(201).entity(request).build();
			}else
				return Response.status(201).build();
			
		}

}
