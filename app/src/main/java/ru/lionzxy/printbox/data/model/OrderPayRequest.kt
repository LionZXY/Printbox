package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName

class OrderPayRequest(
        @SerializedName("order_sum")
        var orderSum: Int) {
    constructor() : this(0)
}