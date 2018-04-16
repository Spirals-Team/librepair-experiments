package br.com.reclameaqui.teste.exceptions;

public class ReclamacaoNaoEncontradaException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ReclamacaoNaoEncontradaException(String pMensagem) {
		super(pMensagem);
	}
	
}
