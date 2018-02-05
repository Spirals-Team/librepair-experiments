package io.descoped.client.external.posten;

import io.descoped.client.exception.APIClientException;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class PostalCode {

    private String code;
    private String place;
    private String communeCode;
    private String communeName;
    private PostalCodeCategory category;

    public PostalCode() {
    }

    public PostalCode(String code, String place, String communeCode, String communeName, PostalCodeCategory category) {
        this.code = code;
        this.place = place;
        this.communeCode = communeCode;
        this.communeName = communeName;
        this.category = category;
    }

    // this fixes issues where a csv line container quote (") within quoted columns, e.g. "1"; "Foo should be "Bar" in FooBar"; "3"
    private static String[] splitLine(String row) {
        return row.split(PostenPostalCodesClient.SAFE_SPLIT_TABBED_REGEXP);
    }


    public static PostalCode valueOf(String rowLine) {
        String[] splitted = splitLine(rowLine);
        if (splitted.length != 5)
            throw new APIClientException(String.format("Wrong number (%s) of row elements!", splitted.length));
        return new PostalCode(splitted[0], splitted[1], splitted[2], splitted[3], PostalCodeCategory.valueOf(splitted[4]));
    }

    public String getCode() {
        return code;
    }

    public String getPlace() {
        return place;
    }

    public String getCommuneCode() {
        return communeCode;
    }

    public String getCommuneName() {
        return communeName;
    }

    public PostalCodeCategory getCategory() {
        return category;
    }

    public NorwayCounty getCounty() {
        return NorwayCounty.asEnum(communeCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostalCode)) return false;

        PostalCode that = (PostalCode) o;

        if (!getCode().equals(that.getCode())) return false;
        if (!getPlace().equals(that.getPlace())) return false;
        if (!getCommuneCode().equals(that.getCommuneCode())) return false;
        if (!getCommuneName().equals(that.getCommuneName())) return false;
        return getCategory().equals(that.getCategory());
    }

    @Override
    public int hashCode() {
        int result = getCode().hashCode();
        result = 31 * result + getPlace().hashCode();
        result = 31 * result + getCommuneCode().hashCode();
        result = 31 * result + getCommuneName().hashCode();
        result = 31 * result + getCategory().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PostalCode{" +
                "code='" + code + '\'' +
                ", place='" + place + '\'' +
                ", communeCode='" + communeCode + '\'' +
                ", communeName='" + communeName + '\'' +
                ", county='" + getCounty() + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
