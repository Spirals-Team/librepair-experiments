package apna.Maholla.mappers;

import apna.Maholla.RequestModels.SignIn;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Users;
import apna.Maholla.model.Verification;

public class GetUserRequestMapper {

    public Users user;
    public Verification verification;

    public GetUserRequestMapper() {
        this.user = new Users();
        this.verification = new Verification();
    }


    public void setUser(SignIn signIn, Apartment apartment) throws Exception {

        this.user.apartmentkey = apartment.getId();
        this.user.block = signIn.block;
        this.user.emailid = signIn.emailid;
        this.user.flatnumber = signIn.flatnumber;
        this.user.image = signIn.image;
        this.user.name = signIn.name;
        this.user.phonenumber = signIn.phonenumber;
        this.user.password = signIn.password;
        this.user.userid = signIn.userid;
        this.user.role = 0;
        setVerification(signIn);
        user.setPassword();
    }

    private void setVerification(SignIn signIn) {
        this.verification.userid = signIn.emailid;
        this.verification.apartment = true;
        this.verification.emailid = false;
        this.verification.phonenumber = false;
    }
}
