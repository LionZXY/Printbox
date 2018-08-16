package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

class OrderPay(
        @SerializedName("pk")
        var id: Int,
        @SerializedName("payer")
        var payer: String,
        @SerializedName("order_sum")
        var orderSum: Int,
        @SerializedName("created_at")
        var createdAt: DateTime,
        @SerializedName("updated_at")
        var updatedAt: DateTime,
        @SerializedName("status")
        var status: String,
        @SerializedName("status_name")
        var statusName: String,
        @SerializedName("payment_method")
        var paymentMethod: String,
        @SerializedName("payment_method_name")
        var paymentMethodName: String,
        @SerializedName("pay_url")
        var payUrl: String) {
    constructor() : this(0, "", 0, DateTime(), DateTime(),
            "", "", "", "", "")
}