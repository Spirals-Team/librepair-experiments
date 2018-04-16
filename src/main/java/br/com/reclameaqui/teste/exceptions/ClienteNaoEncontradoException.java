package br.com.reclameaqui.teste.exceptions;

public class ClienteNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ClienteNaoEncontradoException(String pMensagem) {
		super(pMensagem);
	}
	
}
