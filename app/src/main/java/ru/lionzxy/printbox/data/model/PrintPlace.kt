package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class PrintPlace(
        @SerializedName("pk")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("created_at")
        var createdAt: DateTime,
        @SerializedName("status")
        var status: Int,
        @SerializedName("status_name")
        var statusName: String,
        @SerializedName("image")
        var imageUrl: String,
        @SerializedName("place_description")
        var placeDescription: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("tags")
        var tags: String,
        @SerializedName("x_map_position")
        var latitude: Double,
        @SerializedName("y_map_position")
        var longitude: Double,
        @SerializedName("printer_options_double_page")
        var optionDoublePage: List<PrintOption>,
        @SerializedName("printer_options_color")
        var optionColor: List<PrintOption>,
        @SerializedName("paper_count")
        var paperCount: Int,
        @SerializedName("place")
        var place: Int) {
    constructor() : this(0, "", DateTime(), 0, "",
            "", "", "", "", 0.0,
            0.0, emptyList(), emptyList(),
            0, 0)
}