package nc.noumea.mairie.bilan.energie.web.wm;

import java.util.Map;

/**
 * Information sur la page à afficher
 * 
 * @author David ALEXIS
 * 
 */
public interface PageInfo {

	/**
	 * @return Retourne les paramètres sous forme de map de la page zul
	 */
	public Map<String, Object> getParameter();

	/**
	 * @return Retourne la page zul à afficher
	 */
	public String getZul();

	/**
	 * @return Retourne l'identifiant de la page
	 */
	public PageId getId();
	
	/**
	 * @return Le titre de la page
	 */
	public String getTitle();
	
	/**
	 * @return True si la page peut être fermée par le window manager
	 */
	public boolean isCloseable();
	
}
