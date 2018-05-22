package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName

data class DocumentWrapper(
        @SerializedName("count")
        var count: Int,
        @SerializedName("next")
        var next: String?,
        @SerializedName("previous")
        var previous: String?,
        @SerializedName("results")
        var results: List<PrintDocument>) {
    constructor() : this(0, "", "", emptyList())
}