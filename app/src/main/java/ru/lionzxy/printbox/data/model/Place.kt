package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName

class Place(
        @SerializedName("pk")
        var pk: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("place_code")
        var placeCode: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("address")
        var address: String) {
    constructor() : this(0, "", "", "", "")
}