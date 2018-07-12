package apna.Maholla.mappers;

import apna.Maholla.model.Users;

public class UpdatedUserMapper {
    private Users newUserDetails;
    public Users updatedUser;

    public UpdatedUserMapper(Users newUserDetails, Users updatedUser) {
        this.newUserDetails = newUserDetails;
        this.updatedUser = updatedUser;
    }

    public void setUpdatedUser() {
        this.updatedUser = newUserDetails;
    }

    private void updateName(){
        if(newUserDetails.name != null){
            updatedUser.name = newUserDetails.name;
        }
    }

    private void updateUserId(){
        if(newUserDetails.userid != null){
            updatedUser.userid = newUserDetails.userid;
        }
    }

    private void updatePassword() throws Exception {
        if(newUserDetails.password != null){
            updatedUser.password = newUserDetails.password;
            updatedUser.setPassword();
        }
    }
}
