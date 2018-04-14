package br.com.reclameaqui.teste.exceptions;

public class ReclamacaoException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ReclamacaoException() {
		super();
	}

	public ReclamacaoException(String pMensagem) {
		super(pMensagem);
	}

	public ReclamacaoException(Throwable pException) {
		super(pException);
	}

	public ReclamacaoException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}
