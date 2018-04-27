package nc.noumea.mairie.bilan.energie.test;

import java.security.Principal;

/**
 * Principal pour les jeux de test
 *
 * @author Greg Dujardin
 *
 */
public class PrincipalTest implements Principal {

	private String name = "BilanTest";

	/**
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}
}
