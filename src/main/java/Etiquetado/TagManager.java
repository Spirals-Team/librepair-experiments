package Etiquetado;

import modelo.Etiqueta;
import modelo.Ingrediente;

public class TagManager {
	
	Tagger tagger;
	ProxyEtiquetadoInterface proxyEtiquetado;
	
	public TagManager(ProxyEtiquetadoInterface proxyEtiquetado) {
		this.proxyEtiquetado = proxyEtiquetado;
	}
	
	public Etiqueta buscar(Ingrediente ingrediente) {
		Etiqueta ret = this.tagger.buscar(ingrediente);
		
		if(ret == null) {
			ret = this.proxyEtiquetado.getEtiqueta(ingrediente);
			this.tagger.etiquetar(ingrediente, ret);
			return ret;
		}
		else {
			return ret;
		}
	}
}
