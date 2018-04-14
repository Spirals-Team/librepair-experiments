package com.prussia.play.spring.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

import groovy.text.SimpleTemplateEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroovyTemplateUtil {
	private final static String FOLDER_PATH = "tpl";
	private final static String TEMPLATEFILENAME = "sample";
	private final static String SUFFIX = ".tmpl";

	public static String parseTemplate(String templateFileName, Map<String, Object> binding)
			throws CompilationFailedException, ClassNotFoundException, IOException {
		URL tpl = GroovyTemplateUtil.class.getResource(FOLDER_PATH + File.separator + templateFileName + SUFFIX);
		return new SimpleTemplateEngine().createTemplate(tpl).make(binding).toString();
	}

	public static Object sample(Map<String, Object> bingding, String...arg){
		Map<String, Object> binding = new HashMap<>();
		String content;
		try {
			content = parseTemplate(TEMPLATEFILENAME, binding );
		} catch (Exception e) {
			log.error("Groovy Template", e);
			throw new RuntimeException(e);
		}
		
		return content;
		
	}
}
