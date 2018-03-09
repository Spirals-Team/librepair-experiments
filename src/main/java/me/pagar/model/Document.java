package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document extends PagarMeModel<String> {

    @Expose
    @SerializedName("type")
    private Type type;

    @Expose
    private String number;

    public Document() {
        super();
    }

    public Document(Type type, final String number) {
        this();
        this.type = type;
        this.number = number;
    }

    public Type getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public enum Type {
        @SerializedName("cpf")
        CPF,

        @SerializedName("cnpj")
        CNPJ
    }
}
