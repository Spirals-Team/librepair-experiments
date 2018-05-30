package com.economizate.transferencias;

import com.economizate.entidades.Cuenta;

public class ProxyTransferencia implements ITransferencia{

	private GeneradorTransferencia transferencia;
	
	public ProxyTransferencia(Cuenta cuenta) {
		super();
		this.transferencia = new GeneradorTransferencia(cuenta);
	}

	@Override
	public boolean transferir(double monto, String destinatario) {
		return transferencia.transferir(monto, destinatario);
	}
	
	@Override
	public int ejecutar(double monto, String destinatario) {
		return transferencia.ejecutar(monto, destinatario);
	}

}
