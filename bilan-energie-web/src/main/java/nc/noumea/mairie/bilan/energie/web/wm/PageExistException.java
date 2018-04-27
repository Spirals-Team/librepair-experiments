package nc.noumea.mairie.bilan.energie.web.wm;

import org.zkoss.zk.ui.Component;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;

/**
 * Exception levée lorsque l'on demande l'affichage d'une page et que celle ci
 * est déjà présente dans le WindowManager
 * 
 * @author David ALEXIS
 */
public class PageExistException extends BusinessException {

	private static final long serialVersionUID = 1L;

	/**
	 * Information sur la page affichée
	 */
	private PageInfo pageInfo;

	/**
	 * Page affichée
	 */
	private Component page;

	/**
	 * Constrcuteur par défaut
	 * 
	 * @param pageInfo
	 *            information sur la page
	 * @param page
	 *            page affihée
	 */
	public PageExistException(PageInfo pageInfo, Component page) {
		this.page = page;
		this.pageInfo = pageInfo;
	}

	/**
	 * @return {@link #pageInfo}
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @return {@link #page}
	 */
	public Component getPage() {
		return page;
	}
}
