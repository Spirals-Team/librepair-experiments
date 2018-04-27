package nc.noumea.mairie.bilan.energie.core.converter;

import java.util.List;

/**
 * Interface de conversion des beans d'un format à un autre
 * 
 * @author Greg Dujardin
 */
public interface ConvertManager {

	/**
	 * Convertit un objet src en un nouvel objet de type T
	 * 
	 * @param <T>
	 * 			  Type générique
	 * @param src
	 *            objet source
	 * @param dst
	 *            type destination
	 * @return nouvel objet
	 */
	public <T> T convert(Object src, Class<T> dst);

	/**
	 * Copie un objet src dans un objet existant
	 * 
	 * @param <T>
	 * 			  Type générique
	 * @param src
	 *            objet source
	 * @param dst
	 *            objet destination
	 * @return destination
	 */
	public <T> T convert(Object src, T dst);

	/**
	 * Convertit une liste d'objets src en une liste de nouveaux objets de type
	 * T
	 * 
	 * @param <T>
	 * 			  Type générique
	 * @param src
	 *            objet source
	 * @param dst
	 *            type destination
	 * @return nouvel objet
	 */
	public <T> List<T> convertList(List<?> src, Class<T> dst);

}
