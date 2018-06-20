package ru.lionzxy.printbox.data.api

import com.google.gson.annotations.SerializedName

class PrintOrderApi(
        @SerializedName("document")
        var document: Int,
        @SerializedName("printer")
        var printer: Int,
        @SerializedName("copies")
        var copies: Int,
        @SerializedName("color_option")
        var color_option: Int,
        @SerializedName("duplex_option")
        var duplex_option: Int) {
    constructor() : this(0, 0, 0, 0, 0)
}