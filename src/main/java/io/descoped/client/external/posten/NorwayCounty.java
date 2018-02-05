package io.descoped.client.external.posten;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public enum NorwayCounty {

    F00("Unknown"),
    F01("Østfold"),
    F02("Akershus"),
    F03("Oslo"),
    F04("Hedmark"),
    F05("Oppland"),
    F06("Buskerud"),
    F07("Vestfold"),
    F08("Telemark"),
    F09("Aust-Agder"),
    F10("Vest-Agder"),
    F11("Rogaland"),
    F12("Hordaland"),
    F14("Sogn og Fjordane"),
    F15("Møre og Romsdal"),
    F16("Sør-Trøndelag"),
    F17("Nord-Trøndelag"),
    F18("Nordland"),
    F19("Troms"),
    F20("Finnmark"),
    F21("Svalbard"),
    F22("Jan Mayen"),
    F23("Kontinentalsokkelen"),
    F50("Trøndelag");

    private String description;

    NorwayCounty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static NorwayCounty asEnum(String communeCode) {
        try {
            String code = "F" + communeCode.substring(0, 2);
            return valueOf(code);
        } catch (Exception e) {
            return valueOf("F00");
        }
    }

    @Override
    public String toString() {
        return description;
    }
}
