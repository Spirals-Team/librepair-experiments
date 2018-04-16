package br.com.reclameaqui.teste.exceptions;

public class JsonConverterException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public JsonConverterException(String pMensagem) {
		super(pMensagem);
	}
	
}
