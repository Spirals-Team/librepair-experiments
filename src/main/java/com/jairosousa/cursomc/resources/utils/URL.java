package com.jairosousa.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	/**
	 * Descodificar um paramentro
	 * @param s
	 * @return string
	 */
	public static String decoderParam(String s){
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Converte parametros de String para lista de Integer
	 * @param s
	 * @return list
	 */
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");// quebra os elementos da lista acrescentando a "virgula"
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		/**
		 * O metodo acima pode ser feito por lambda do Java 8
		 * Codigo abaixo
		 */
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

}
