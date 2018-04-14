package br.com.reclameaqui.teste.exceptions;

import org.springframework.validation.BindingResult;

public class CampoNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;
	private static BindingResult bindingResult; 
	
	public CampoNaoEncontradoException() {
		super();
	}

	public CampoNaoEncontradoException(String pMensagem) {
		super(pMensagem);
	}
	
	public CampoNaoEncontradoException(BindingResult binding) {
		super("Campos Obrigatorios");		
		this.bindingResult = binding;
	}

	public CampoNaoEncontradoException(Throwable pException) {
		super(pException);
	}

	public CampoNaoEncontradoException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
	
	public BindingResult getBindingResult() {
        return bindingResult;
    }
}
