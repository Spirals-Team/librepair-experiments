package nc.noumea.mairie.bilan.energie.web;

import org.zkoss.zk.ui.util.Clients;

/**
 * Type de notification
 * 
 * @author David ALEXIS
 *
 */
public enum NotificationType {

	/**
	 * Message de type "Erreur"
	 */
	ERROR(Clients.NOTIFICATION_TYPE_ERROR),
	
	/**
	 * Message de type "Information"
	 */
	WARNING(Clients.NOTIFICATION_TYPE_WARNING),
	
	/**
	 * Type par défaut
	 */
	INFO(Clients.NOTIFICATION_TYPE_INFO);
	
	/**
	 * Correspondance ZK
	 */
	private String zkNotification;
	
	/**
	 * Constructeur par défaut
	 * 
	 * @param zkNotification voir {@link #zkNotification}
	 */
	private NotificationType(String zkNotification){
		this.zkNotification = zkNotification;
	}

	/**
	 * @return {@link #zkNotification}
	 */
	public String getZkNotification() {
		return zkNotification;
	}
}
