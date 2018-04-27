package nc.noumea.mairie.bilan.energie.web.wm;

import org.zkoss.zk.ui.Component;

/**
 * Rerprésente une page affichée
 * 
 * @author David ALEXIS
 *
 */
public class Page {

	/**
	 * Information de la page
	 */
	private PageInfo info;

	/**
	 * Composant
	 */
	private Component data;

	/**
	 * Constructeur
	 * 
	 * @param info
	 *            PageInfo de la page
	 * @param data
	 *            Componant
	 */
	public Page(PageInfo info, Component data) {
		this.data = data;
		this.info = info;
	}

	/**
	 * @return {@link #info}
	 */
	public PageInfo getInfo() {
		return info;
	}

	/**
	 * @return {@link #data}
	 */
	public Component getData() {
		return data;
	}
}
