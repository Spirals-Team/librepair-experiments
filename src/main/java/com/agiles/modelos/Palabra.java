package com.agiles.modelos;

public class Palabra {
	private char[] letras;
	
	public Palabra() {
		letras = new char[4];
	}
	
	public Palabra(String palabra) {
		this();
		setPalabra(palabra);
	}
	
	public void setPalabra(String palabra) {
		for(int i = 0;i < letras.length;i++) {
			letras[i] = palabra.toCharArray()[i];
		}
	}
	
	public char[] getPalabra() {
		return letras;
	}
}
