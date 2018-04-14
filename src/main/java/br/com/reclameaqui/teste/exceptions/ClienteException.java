package br.com.reclameaqui.teste.exceptions;

public class ClienteException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ClienteException() {
		super();
	}

	public ClienteException(String pMensagem) {
		super(pMensagem);
	}

	public ClienteException(Throwable pException) {
		super(pException);
	}

	public ClienteException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}
