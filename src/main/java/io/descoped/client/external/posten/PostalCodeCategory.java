package io.descoped.client.external.posten;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public enum  PostalCodeCategory {
    
    B("Både gateadresser og postbokser"),
    F("Flere bruksområder (felles)"),
    G("Gateadresser (og stedsadresser), dvs “grønne postkasser”"),
    P("Postbokser"),
    S("Servicepostnummer (disse postnumrene er ikke i bruk til postadresser)");

    private String description;

    PostalCodeCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name();
    }
}
