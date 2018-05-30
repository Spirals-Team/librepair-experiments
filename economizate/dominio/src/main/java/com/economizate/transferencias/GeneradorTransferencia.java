package com.economizate.transferencias;

import java.util.Date;

import com.economizate.entidades.Cuenta;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.impl.Propiedad;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class GeneradorTransferencia implements ITransferencia{
	
	private Cuenta cuenta;
	
	public GeneradorTransferencia(Cuenta cuenta) {
		super();
		this.cuenta = cuenta;
	}

	@Override
	public boolean transferir(double monto, String destinatario) {
		boolean result = false;
		
		try {
			
			HttpRequest request = Unirest.post(Propiedad.getInstance().getPropiedad("endpoint"))
			  .header("accept", "application/json")
			  .routeParam("monto", String.valueOf(monto));
			
			HttpResponse<JsonNode> response = request.asJson();
			System.out.println(request.getUrl());
			
			result = true;
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		generarEgreso();
		return result;
	}
	
	@Override
	public int ejecutar(double monto, String destinatario) {
		HttpResponse<JsonNode> response = null;
		
		try {
			
			HttpRequest request = Unirest.post(Propiedad.getInstance().getPropiedad("endpoint"))
			  .header("accept", "application/json")
			  .routeParam("monto", String.valueOf(monto));
			
			response = request.asJson();
			System.out.println(request.getUrl());
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		generarEgreso();
		return response.getStatus();
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	public void generarEgreso() {
		
		MovimientoMonetario egreso = 
				new MovimientoMonetario("Gasto shopping", "Renovar ropa", Double.parseDouble("-96"), new Date());
		
		cuenta.getMovimientos().agregarMovimiento(egreso);
		cuenta.modificarTotal(cuenta.getMovimientos().getTotal());
		
		cuenta.setTotal(cuenta.getTotal());
	}

}
