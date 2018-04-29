package com.ts.previsao.tempo.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class CommonsUtils {

	public static final Pattern DIACRITICS = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

	public static String removeXMLMetaData(String xml) {
		return xml.replace("<?xml version='1.0' encoding='ISO-8859-1'?>", "");
	}

	public static String padronizaNomeDeCidade(String nomeCidade) {
		String cidade;
		cidade = removeAcentos(nomeCidade);
		cidade = cidade.replace("-", " ");
		return cidade.toLowerCase();
	}

	public static String removeAcentos(String string) {
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = DIACRITICS.matcher(string).replaceAll("");
		return string;
	}

}