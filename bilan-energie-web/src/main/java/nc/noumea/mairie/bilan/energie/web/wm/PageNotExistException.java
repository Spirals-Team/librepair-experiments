package nc.noumea.mairie.bilan.energie.web.wm;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;

/**
 * Exception levée lorsque l'on demande l'affichage d'une page et que celle ci
 * est déjà présente dans le WindowManager
 * 
 * @author David ALEXIS
 */
public class PageNotExistException extends BusinessException {

	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant de la page affichée
	 */
	private PageId pageId;

	/**
	 * Constrcuteur par défaut
	 * 
	 * @param pageId
	 *            Identifiant de la page
	 */
	public PageNotExistException(PageId pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return {@link #pageId}
	 */
	public PageId getPageId() {
		return pageId;
	}

}
