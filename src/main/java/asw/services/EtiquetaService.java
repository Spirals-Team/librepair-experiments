package asw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.entities.Etiqueta;
import asw.repository.EtiquetaRepository;

@Service
public class EtiquetaService {
	@Autowired
	public EtiquetaRepository etiquetaRepository;
	
	public List<Etiqueta> getEtiquetas(){
		return etiquetaRepository.findAll();
	}
	
	
	public String etiquetaMasUsada() {
		List<String> yaUsada = new ArrayList<String>();
		String etiqueta = "";
		int cont = 0;
		int aux = 0;
		for (Etiqueta eq: getEtiquetas()) {
			if(!yaUsada.contains(eq.getValor())) {
				yaUsada.add(eq.getValor());
				aux = numeroRepeticionesEtiqueta(eq.getValor());
				if(cont < aux) {
					cont = aux;
					etiqueta = eq.getValor();
				}
				
			}
		}
		return etiqueta;
	}
	
	public int numeroRepeticionesEtiqueta(String n) {
		int cont = 0;
		for (Etiqueta eq : getEtiquetas()) {
			if(eq.getValor().equals(n))
				cont++;
		}
		return cont;
	}
}
