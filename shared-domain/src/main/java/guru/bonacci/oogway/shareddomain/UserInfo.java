package guru.bonacci.oogway.shareddomain;

public interface UserInfo {

    String getUsername(); 

	void setUsername(String username);

    String getEncryptedPassword();

	void setEncryptedPassword(String pw);

    String getApiKey();
    
	void setApiKey(String apiKey);
}


