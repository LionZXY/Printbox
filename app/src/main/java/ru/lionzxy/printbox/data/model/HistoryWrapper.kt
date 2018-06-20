package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName

class HistoryWrapper(
        @SerializedName("count")
        var count: Int,
        var next: String?,
        var previous: String?,
        var results: List<PrintHistory>) {
        constructor(): this(0, null, null, emptyList())
}