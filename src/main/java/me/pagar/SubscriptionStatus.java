package me.pagar;

import com.google.gson.annotations.SerializedName;

public enum SubscriptionStatus {

    @SerializedName("trialing")
    TRIALING,

    @SerializedName("paid")
    PAID,

    @SerializedName("pending_payment")
    PENDING_PAYMENT,

    @SerializedName("unpaid")
    UNPAID,

    @SerializedName("canceled")
    CANCELED,

    @SerializedName("ended")
    ENDED;
}
