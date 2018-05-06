package es.uniovi.asw.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import es.uniovi.asw.parser.agents.AbstractAgent;

/**
 * @author Jorge
 * 
 */
public interface ReadList {
	Set<AbstractAgent> parse(String ruta);

	HashMap<Integer, String> parseMaster(String ruta);
	
	HashMap<Integer, String> getMasterKinds();
}
