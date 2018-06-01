package com.economizate.transferencias;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface ITransferencia {
	
	public boolean transferir (double monto, String destinatario) throws UnirestException ;
	
	public int ejecutar (double monto, String destinatario) throws UnirestException ;

}
