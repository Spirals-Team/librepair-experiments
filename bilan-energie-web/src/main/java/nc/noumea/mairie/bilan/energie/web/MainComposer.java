package nc.noumea.mairie.bilan.energie.web;

import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.wm.TabComposer;

/**
 * Composer Principal
 *
 * @author Greg Dujardin
 *
 */
public class MainComposer extends TabComposer {

	private static final long serialVersionUID = 1L;

	/*
	 * @see nc.noumea.mairie.bilan.energie.web.wm.WMTabComposer#getWorkspace()
	 */
	protected Tabbox getWorkspace() throws TechnicalException {

		// Récupération du workspace
		Tabbox workspace = (Tabbox) Selectors.find(Path.getComponent("/"),
				"window[id=main] tabbox[id=workspace]").get(0);

		return workspace;
	}

	/**
	 * Permet d'afficher une notification
	 * 
	 * @param notification
	 *            Notification à afficher
     * @throws TechnicalException Exception technique
	 */
	public void showNotification(Notification notification)
			throws TechnicalException {

		Clients.showNotification(notification.getMessage(), notification
				.getType().getZkNotification(), null, "bottom_right", 4000,
				true);
	}

	/**
	 * Permet d'afficher un message d'erreur
	 * 
	 * @param titre Titre de message
	 * @param message
	 *            Notification à afficher
     * @throws TechnicalException Exception technique
	 */
	public void showError(String titre, String message)
			throws TechnicalException {

		Messagebox.show(message, titre, Messagebox.OK, Messagebox.ERROR);
	}

}
