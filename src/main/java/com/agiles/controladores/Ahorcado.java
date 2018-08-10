package com.agiles.controladores;

import java.util.ArrayList;

import com.agiles.modelos.Palabra;

public class Ahorcado {
	private ArrayList<Palabra> palabras;
	private char[] palabraActual;
	private char[] letrasIngresadas;
	private int cantidadLetrasIngresadas;
	private int erroresRestantes;
	
	public Ahorcado() {
		cantidadLetrasIngresadas = 0;
		erroresRestantes = 4;
		
		palabras = new ArrayList<Palabra>();
		palabras.add(new Palabra("hola"));
		seleccionarPalabra("hola");
		letrasIngresadas = new char[palabraActual.length+erroresRestantes];
	}
	
	public void seleccionarPalabra(String palabraSeleccionada) {
		for(Palabra palabra : palabras) {
			if(new String(palabra.getPalabra()).equals(palabraSeleccionada)) {
				palabraActual = palabra.getPalabra();
				return;
			}
		}
		
		Palabra seleccionada = new Palabra(palabraSeleccionada);
		palabras.add(seleccionada);
		palabraActual = seleccionada.getPalabra();
	}
	
	
	
	public void ingresarLetra(char letra) {
		
		for(char letraI:letrasIngresadas){
			if(letraI==letra){
				return;
				/*para verificar que no este ya*/
			}
		}
		letrasIngresadas[cantidadLetrasIngresadas] = letra;
		cantidadLetrasIngresadas++;
		
		boolean existeEnPalabra = false;
	
		for(char letraPalabra : palabraActual) {
			if(letraPalabra == letra) {
				existeEnPalabra = true;
			}
		}
		
		if(!existeEnPalabra) {
			erroresRestantes--;
		}
		
	}
	
	public int getErroresRestantes() {
		return erroresRestantes;
	}
	
	public boolean isGanador() {
		
		
		/*contar caracteres diferentes*/
		char[] letrasRepetidas=new char[palabraActual.length];
		char[] letrasDiff=new char[palabraActual.length];
		int cantRep=0;
		int cantDiff=0;
		for(int i=0;i<palabraActual.length;++i){
			boolean repetida=false;
			for(int j=i+1;j<palabraActual.length;++j){
				if(palabraActual[i]==palabraActual[j]){
					repetida=true;
					boolean esta=false;
					for(char lr:letrasRepetidas){
						if(lr==palabraActual[i]){
							esta=true;
						}
					}
					if(esta){
						continue;
					}
					else{
						letrasRepetidas[cantRep]=palabraActual[i];
						++cantRep;
						letrasDiff[cantDiff]=palabraActual[i];
						++cantDiff;
					}
				}
			}
			if(!repetida){
				letrasDiff[cantDiff]=palabraActual[i];
				++cantDiff;
			}
		}
		
		int letrasBien=0;
		for(char letraIngresada : letrasIngresadas) {
			for(char letraPalabra : letrasDiff) {
				if(letraIngresada == letraPalabra) {
					++letrasBien;
				}
			} 
			
		}
		
		if(letrasBien >= cantDiff && erroresRestantes >= 0) {
			return true;
		} 
		
		return false;
	}
	
	public String getLetrasUsadas() {
		String concatenado = "";
		for(char letra : letrasIngresadas) {
			if(letra != '\u0000') {
				concatenado+= letra;
			}
		}
		return concatenado;
	}
	
	public String mostrarPalabra() {
		String palabraMostrar = "";
		
		boolean letraExiste = false;
		
		for(char letraPalabra : palabraActual) {
			letraExiste = false;
			
			for(char letraIngresada : letrasIngresadas) {
				if(letraIngresada == letraPalabra) {
					letraExiste = true;
				}
			} 
			
			if(letraExiste) {
				palabraMostrar += letraPalabra+" ";
			} else {
				palabraMostrar += "_ ";
			}
		}
		
		return palabraMostrar;
	}
	
	public void ingresarPalabra(String palabra) {
		for(char letra : palabra.toCharArray()) {
			ingresarLetra(letra);
		}
	}
	
	public int getCantidadPalabras() {
		return palabras.size();
	}
	
	public void addPalabra(String palabra) {
		palabras.add(new Palabra(palabra));
		letrasIngresadas = new char[palabra.length()+erroresRestantes];
	}
	
	public String getPalabraActual(){
		return new String(this.palabraActual);
	}
}
