package nc.noumea.mairie.bilan.energie.web;

/**
 * Notification à afficher
 * 
 * @author David ALEXIS
 *
 */
public class Notification {

	/**
	 * Texte de la notification
	 */
	private String message;
	
	/**
	 * Type de la notification
	 */
	private NotificationType type = NotificationType.INFO;

	/**
	 * Constructeur avec un message et un type
	 * 
	 * @param message voir {@link #message}
	 * @param type voir {@link #type}
	 */
	public Notification(String message, NotificationType type) {
		super();
		this.message = message;
		this.type = type;
	}

	/**
	 * Constructeur avec un message
	 * 
	 * @param message voir {@link #message}
	 */
	public Notification(String message) {
		super();
		this.message = message;
	}

	/**
	 * Constructeur par défaut
	 */
	public Notification() {
	}

	/**
	 * @return {@link #message}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message {@link #message}
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return {@link #type}
	 */
	public NotificationType getType() {
		return type;
	}

	/**
	 * @param type {@link #type}
	 */
	public void setType(NotificationType type) {
		this.type = type;
	}

}
