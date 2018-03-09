package me.pagar;

import com.google.gson.annotations.SerializedName;

public enum BankAccountType {
    @SerializedName("conta_corrente")
    CORRENTE,
    @SerializedName("conta_poupanca")
    POUPANCA,
    @SerializedName("conta_corrente_conjunta")
    CORRENTE_CONJUNTA,
    @SerializedName("conta_poupanca_conjunta")
    POUPANCA_CONJUNTA
}
