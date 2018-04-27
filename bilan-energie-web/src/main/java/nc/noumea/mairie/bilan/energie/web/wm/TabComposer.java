package nc.noumea.mairie.bilan.energie.web.wm;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;

import org.springframework.context.MessageSource;
import org.zkoss.bind.BindComposer;
import org.zkoss.bind.Binder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

/**
 * Composeur permettant de gérer des panels de type "Tab"
 * 
 * @author David ALEXIS
 * 
 */
public abstract class TabComposer extends BindComposer<Window> {

	private static final long serialVersionUID = 1L;

	private MessageSource messageSource = (MessageSource) SpringUtil.getBean("messageSource");

	/**
	 * Evènement lors de la fermeture d'une table
	 */
	private EventListener<Event> closeTabListener = new SerializableEventListener<Event>() {
		private static final long serialVersionUID = 1L;

		public void onEvent(Event event) throws Exception {

			// Recherche de l'ID de la table fermée
			Tab tab = (Tab) event.getTarget();

			// Parcours des page du cache
			for (PageId id : pageMap.keySet()) {
				InternalPage page = pageMap.get(id);

				// Si trouvé
				if (page.getTab().equals(tab)) {

					event.stopPropagation();
					closePage(id);
					break;
				}
			}
		}
	};

	/**
	 * Evènement lors de la selection d'une table
	 */
	private EventListener<Event> selectTabListener = new SerializableEventListener<Event>() {
		private static final long serialVersionUID = 1L;

		public void onEvent(Event event) throws Exception {

			// Recherche de l'ID de la table fermée
			Tab tab = (Tab) event.getTarget();

			// Parcours des page du cache
			for (PageId id : pageMap.keySet()) {
				InternalPage page = pageMap.get(id);

				// Si trouvé
				if (page.getTab().equals(tab)) {
					navigation.add(id);
					break;
				}
			}
		}
	};

	/**
	 * Information interne sur la page
	 */
	private static class InternalPage {

		/**
		 * Page réèle
		 */
		private Page reelPage;

		/**
		 * Conteneur de la page
		 */
		private Tabpanel content;

		/**
		 * Tab panel
		 */
		private Tab tab;

		/**
		 * Permet de savoir si une page est dirty
		 */
		private boolean dirty = false;

		/**
		 * Constructeur par défaut
		 * 
		 * @param reelPage
		 *            Page affiché
		 * @param content
		 *            Conteneur de la page
		 * @param tab
		 *            Tab panel
		 */
		public InternalPage(Page reelPage, Tabpanel content, Tab tab) {
			this.reelPage = reelPage;
			this.content = content;
			this.tab = tab;
		}

		/**
		 * @return {@link #reelPage}
		 */
		public Page getReelPage() {
			return reelPage;
		}

		/**
		 * @return {@link #content}
		 */
		public Tabpanel getContent() {
			return content;
		}

		/**
		 * @return {@link #tab}
		 */
		public Tab getTab() {
			return tab;
		}

		/**
		 * @return {@link #dirty}
		 */
		public boolean isDirty() {
			return dirty;
		}

		/**
		 * @param dirty
		 *            Voir {@link #dirty}
		 */
		public void setDirty(boolean dirty) {
			this.dirty = dirty;
		}
	};

	/**
	 * Liste des pages affichée
	 */
	private Map<PageId, InternalPage> pageMap = new Hashtable<PageId, InternalPage>();

	/**
	 * Objet de navigation
	 */
	private Navigation<PageId> navigation = new Navigation<PageId>();

	/**
	 * Affiche une page
	 * 
	 * @param pageInfo
	 *            Page à afficher
	 * @return Page affichée
     * @throws TechnicalException Exception technique
	 * @throws PageExistException
	 *             Levée si une page existe déjà
	 */
	public Page showPage(PageInfo pageInfo) throws TechnicalException,
			PageExistException {

		// Récupération du workspace
		Tabbox workspace = getWorkspace();

		if (pageMap.containsKey(pageInfo.getId())) {

			InternalPage page = pageMap.get(pageInfo.getId());
			throw new PageExistException(page.getReelPage().getInfo(), page
					.getReelPage().getData());
		}

		// Récupération du workspacetabs
		Tabs workspaceTabs = workspace.getTabs();

		// Récupération du workspacepanels
		Tabpanels workspaceTabPanels = workspace.getTabpanels();

		// Création du nouvel onglet
		Tab newTab = new Tab();
		newTab.setLabel(pageInfo.getTitle());
		newTab.setClosable(pageInfo.isCloseable());

		Tabpanel newPanel = new Tabpanel();
		workspaceTabPanels.appendChild(newPanel);
		workspaceTabs.appendChild(newTab);

		// Création du composant zul dans le nouvel onglet
		Component cmp = Executions.createComponents(pageInfo.getZul(),
				newPanel, pageInfo.getParameter());
		Page page = new Page(pageInfo, cmp);
		InternalPage internalPage = new InternalPage(page, newPanel, newTab);
		pageMap.put(pageInfo.getId(), internalPage);

		// afficher la page
		try {
			showPage(pageInfo.getId());
		} catch (PageNotExistException e) {
			throw new WebTechnicalException(e.getMessage(), e);
		}

		// Création de l'event pour gérer la fermeture

		newTab.addEventListener(Events.ON_CLOSE, closeTabListener);
		newTab.addEventListener(Events.ON_SELECT, selectTabListener);

		return page;
	}

	/**
	 * Afficher une page déjà présente dans le Window Manager
	 * 
	 * @param id
	 *            identifiant de la page
	 * @return Page affichée
     * @throws TechnicalException Exception technique
	 * @throws PageNotExistException
	 *             Exception levée si la page n'a pas été trouvée
	 */
	public Page showPage(PageId id) throws TechnicalException,
			PageNotExistException {

		PageId curentId = navigation.getCurrent();

		if (curentId != null && curentId.equals(id))
			return getCurrentPage();

		if (!pageMap.containsKey(id))
			throw new PageNotExistException(id);

		Tabbox workspace = getWorkspace();

		InternalPage intPage = pageMap.get(id);
		workspace.setSelectedPanel(intPage.getContent());

		navigation.add(id);

		return intPage.getReelPage();
	}

	/**
	 * Force la fermeture d'une page
	 * 
	 * @param id
	 *            Id de la page à fermer
     * @throws TechnicalException Exception technique
	 */
	private void forceClose(PageId id) throws TechnicalException {
		InternalPage page = pageMap.get(id);

		Tabbox workspace = getWorkspace();
		workspace.getTabs().removeChild(page.getTab());
		workspace.getTabpanels().removeChild(page.getContent());

		PageId curentId = navigation.getCurrent();
		if (id.equals(curentId)) {
			last();
		}

		removeCache(id);
	}

	/**
	 * Fermer la page déjà présente dans le Window Manager
	 * 
	 * @param id
	 *            identifiant de la page.
     * @throws TechnicalException Exception technique
	 * @throws PageNotExistException
	 *             Exception levée si la page n'a pas été trouvée
	 */
	public void closePage(final PageId id) throws TechnicalException,
			PageNotExistException {

		if (!pageMap.containsKey(id))
			throw new PageNotExistException(id);

		final InternalPage page = pageMap.get(id);

		// Si la page est dirty, alors poser la question à l'utilisateur
		if (page.isDirty()) {
			String str = messageSource.getMessage("question.enregistrement",
					new Object[] {}, Locale.FRANCE);
			
			Messagebox.show(str, messageSource.getMessage("titre.confirmationfermeture", new Object[]{}, Locale.FRANCE), Messagebox.YES | Messagebox.NO
					| Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener<Event>() {

						public void onEvent(Event event) throws Exception {

							if (((Integer) event.getData()).intValue() == Messagebox.YES) {

								Binder binder = (Binder) page.getReelPage()
										.getData().getAttributes()
										.get("binder");

								Map<String, Object> params = new Hashtable<String, Object>();
								params.put("close", true);
								params.put("pageId", id);
								binder.postCommand("save", params);
							}

							if (((Integer) event.getData()).intValue() == Messagebox.NO) {
								forceClose(id);
							}

							if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
								// On ne fait rien
							}

						}
					});
		} else
			// Fermer la page
			forceClose(id);
	}

	/**
	 * Permet d'afficher la page précédente à la page courant
	 * 
	 * @return Nouvelle page affichée
	 * @throws TechnicalException Exception technique
	 */
	private Page last() throws TechnicalException {

		PageId id = navigation.last();

		if (id != null) {
			Tabbox workspace = getWorkspace();
			InternalPage intPage = pageMap.get(id);
			workspace.setSelectedPanel(intPage.getContent());
			workspace.invalidate();
		} else { // S'il n'y a plus rien dans la liste de navigation

			if (pageMap.size() > 0) {
				try {
					showPage(pageMap.keySet().iterator().next());
				} catch (PageNotExistException e) {
					throw new WebTechnicalException(e);
				}
			}
		}

		return getCurrentPage();
	}

	/**
	 * Ferme la page en cours
	 * 
     * @throws TechnicalException Exception technique
	 */
	public void closeCurrentPage() throws TechnicalException {

		if (navigation.getCurrent() == null)
			return;

		try {
			closePage(getCurrentPage().getInfo().getId());
		} catch (PageNotExistException e) {
			throw new WebTechnicalException(e.getMessage(), e);
		}
	}

	/**
	 * Renvoi la page courante
	 * 
	 * @return Retourne la page courante. Null si aucune page n'est affichée
	 * @throws TechnicalException Exception technique
	 */
	public Page getCurrentPage() throws TechnicalException {

		PageId curentId = navigation.getCurrent();

		// Si aucune page affichée
		if (curentId == null)
			return null;

		return pageMap.get(curentId).getReelPage();
	}

	/**
	 * Retourne la page représenté par l'ID
	 * 
	 * @param id
	 *            Identifiant de la page
	 * @return null si la page n'existe pas
	 * @throws TechnicalException Exception technique
	 */
	public Page getPage(PageId id) throws TechnicalException {
		return pageMap.get(id).getReelPage();
	}

	/**
	 * Vérifier l'existance d'une page dans le window manager
	 * 
	 * @param id
	 *            Identifiant de la page
	 * @return true s'il se trouve dans le window manager
	 * @throws TechnicalException Exception technique
	 */
	public boolean isPageExist(PageId id) throws TechnicalException {
		return !(getPage(id) == null);
	}

	/**
	 * Renvoi le workspace
	 * 
	 * @return Retourne les workspace
	 * @throws TechnicalException Exception technique
	 */
	protected abstract Tabbox getWorkspace() throws TechnicalException;

	/**
	 * Supprime de le cache des page dans le window manager
	 * 
	 * @param id
	 *            identifiant de la page
	 */
	private void removeCache(PageId id) {

		pageMap.remove(id);
		navigation.removeAndGetBefore(id);
	}

	/**
	 * Enregistrement la vue courante comme Dirty
	 * 
	 * @param dirty
	 *            True ou false
	 */
	public void setDirtyOnCurrentPage(boolean dirty) {

		PageId curentId = navigation.getCurrent();
		InternalPage iPage = pageMap.get(curentId);

		String title = iPage.getTab().getLabel();
		if (dirty && !iPage.isDirty())
			iPage.getTab().setLabel(title + "*");
		if (!dirty && iPage.isDirty())
			iPage.getTab().setLabel(title.substring(0, title.length() - 1));

		iPage.setDirty(dirty);
	}

	/**
	 * Permet de savoir si une page est dirty
	 * 
	 * @param pageId Identifiant de la page
	 * @return true si la page est dirty
	 */
	public boolean isDirty(PageId pageId) {
		InternalPage iPage = pageMap.get(pageId);
		return iPage.isDirty();
	}

	/**
	 * Retourne l'ID de la page par rapport au composant racine
	 * 
	 * @param win
	 *            Composant racine
	 * @return Id de la page
	 */
	public PageId getPageIdByComponent(Window win) {

		for (PageId id : pageMap.keySet()) {

			InternalPage iPage = pageMap.get(id);

			if (iPage.getReelPage().getData() == win)
				return id;
		}

		return null;
	}
}
