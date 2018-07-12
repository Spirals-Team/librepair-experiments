package apna.Maholla.ResponceModel;

public class User {
    public String userid;
    public String emailid;
    public long phonenumber;
    public String image;
    public String name;
    public String block;
    public String flatnumber;
    public String role;
    public String apartmentName;
    public Boolean emailVerified;
    public Boolean phoneNumberVerified;
    public Boolean apartmentVerified;

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setFlatnumber(String flatnumber) {
        this.flatnumber = flatnumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setPhoneNumberVerified(Boolean phoneNumberVerified) {
        this.phoneNumberVerified = phoneNumberVerified;
    }

    public void setApartmentVerified(Boolean apartmentVerified) {
        this.apartmentVerified = apartmentVerified;
    }
}
