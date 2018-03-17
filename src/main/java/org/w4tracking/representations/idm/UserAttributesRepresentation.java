package org.w4tracking.representations.idm;

public class UserAttributesRepresentation implements AttributesRepresentation {

    private String username;
    private String email;
    private String fullName;
    private String identityId;
    private String identityProvider;

    public UserAttributesRepresentation() {

    }

    private UserAttributesRepresentation(Builder builder) {
        this.setUsername(builder.username);
        this.setEmail(builder.email);
        this.setFullName(builder.fullName);
        this.setIdentityId(builder.identityId);
        this.setIdentityProvider(builder.identityProvider);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private String username;
        private String email;
        private String fullName;
        private String identityId;
        private String identityProvider;

        public UserAttributesRepresentation build() {
            return new UserAttributesRepresentation(this);
        }

        public Builder withName(String name) {
            this.username = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setIdentityId(String identityId) {
            this.identityId = identityId;
            return this;
        }

        public Builder setIdentityProvider(String identityProvider) {
            this.identityProvider = identityProvider;
            return this;
        }
    }
}
