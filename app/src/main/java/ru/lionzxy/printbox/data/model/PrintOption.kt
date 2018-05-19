package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName

data class PrintOption(
        @SerializedName("pk")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("category")
        var category: String,
        @SerializedName("get_category_display")
        var categoryName: String) {
    constructor() : this(0, "", "", "")
}