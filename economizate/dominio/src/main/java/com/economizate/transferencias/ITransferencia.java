package com.economizate.transferencias;

public interface ITransferencia {
	
	public boolean transferir (double monto, String destinatario);
	
	public int ejecutar (double monto, String destinatario);

}
