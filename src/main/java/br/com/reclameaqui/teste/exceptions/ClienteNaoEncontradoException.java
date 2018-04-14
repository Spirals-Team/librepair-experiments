package br.com.reclameaqui.teste.exceptions;

import org.springframework.validation.BindingResult;

public class ClienteNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;
	private static BindingResult bindingResult; 
	
	public ClienteNaoEncontradoException() {
		super();
	}

	public ClienteNaoEncontradoException(String pMensagem) {
		super(pMensagem);
	}
	
	public ClienteNaoEncontradoException(BindingResult binding) {
		super("Campos Obrigatorios");		
		this.bindingResult = binding;
	}

	public ClienteNaoEncontradoException(Throwable pException) {
		super(pException);
	}

	public ClienteNaoEncontradoException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
	
	public BindingResult getBindingResult() {
        return bindingResult;
    }
}
