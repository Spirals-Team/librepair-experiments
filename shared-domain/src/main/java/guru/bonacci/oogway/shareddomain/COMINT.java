package guru.bonacci.oogway.shareddomain;

import java.io.Serializable;

import lombok.Data;

/**
 * COMINT
 * All intelligence gathered from intercepted communications
 */
@Data
public class COMINT implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String ip;
	private String message;
	
	public COMINT() {}

	public COMINT(String ip, String message) {
		this.ip = ip;
		this.message = message;
	}
}
