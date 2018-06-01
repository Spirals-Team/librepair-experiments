package com.economizate.rest;

import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Usuario;
import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.UsuariosImpl;

@Path("/transferencia")
public class Transferencia {
	
		@Path("/ejecutar")
		@POST
		@Produces("application/json")
		public Response generarTransferencia(@QueryParam("monto") String monto, @QueryParam("destinatario") String destinatario) { 
			
			Logger.getLogger(Transferencia.class.getName()).info("******** Servicio para generar transferencia de"
					+ " monto: " + monto +  " ********");
			
			Logger.getLogger(Transferencia.class.getName()).info("******** Servicio para generar transferencia hacia"
					+ " destinatario: " + destinatario +  " ********");
			
			
			//Genero movimiento al destinatario
			generarIngresoDestinatario(monto, destinatario);
			
			return Response.status(201).build();
			
		}
		
		private void generarIngresoDestinatario(String monto, String destinatario) {
			Usuarios usuarioService = new UsuariosImpl();
			Usuario destino = usuarioService.buscarUsuarioPorEmail(destinatario);
			destino.getSaldo()
			.agregarMovimiento(new MovimientoMonetario("Transferencia", "Entrante", Double.valueOf(monto)));
			//modifico total
			destino.getSaldo().modificarTotal(destino.getSaldo().getMovimientos().getTotal());
			
			Logger.getLogger(Transferencia.class.getName()).info("******** Servicio para generar transferencia de"
					+ " : " + monto  + ", saldo final" + destino.getSaldo().getTotal() + "********");
		}

}
