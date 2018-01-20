package ua.com.company.store.model.entity;

/**
 * Created by Владислав on 17.11.2017.
 */
public class User extends Entity {

    private String nickname;
    private String password;
    private String email;
    private boolean role;
    private boolean isDefaulter;

    public static class UserBuilder {
        private int id;
        private String nickname;
        private String password;
        private String email;
        private boolean role;
        private boolean isDefaulter;

        public UserBuilder id (final int id){
            this.id = id;
            return this;
        }
        public UserBuilder nickname (final String nickname){
            this.nickname = nickname;
            return this;
        }
        public UserBuilder password (final String password){
            this.password = password;
            return this;
        }
        public UserBuilder email (final String email){
            this.email = email;
            return this;
        }
        public UserBuilder role (final boolean role){
            this.role = role;
            return this;
        }
        public UserBuilder isDefaulter (final boolean isDefaulter){
            this.isDefaulter = isDefaulter;
            return this;
        }

        public int getId() {
            return id;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public boolean isRole() {
            return role;
        }

        public boolean isDefaulter() {
            return isDefaulter;
        }

        public User build(){
            return new User(this);
        }

    }

    private User(UserBuilder userBuilder) {
        super(userBuilder.getId());
    this.nickname = userBuilder.getNickname();
    this.password = userBuilder.getPassword();
    this.email = userBuilder.getEmail();
    this.role = userBuilder.isRole();
    this.isDefaulter = userBuilder.isDefaulter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (role != user.role) return false;
        if (isDefaulter != user.isDefaulter) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = nickname != null ? nickname.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role ? 1 : 0);
        result = 31 * result + (isDefaulter ? 1 : 0);
        return result;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User:" + nickname + " " + password + " " + email + " " + role + " " + isDefaulter;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isRole() {
        return role;
    }

    public boolean isDefaulter() {
        return isDefaulter;
    }
}
