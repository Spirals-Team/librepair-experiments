package apna.Maholla.RequestModels;

import apna.Maholla.Miscellaneous.EncryptAndDecrypt;

public class Login {
    public String userid;
    private String password;

    public String getPassword() throws Exception {
        return EncryptAndDecrypt.encrypt(this.password);
    }
}
