package edu.itu.cavabunga.core.entity.authentication;

public enum AuthenticationType {
    ADMIN("Admin"),
    CLIENT("Client"),
    SDG_CLIENT("Sdg");

    private String text;

    AuthenticationType(String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return this.text;
    }
}
