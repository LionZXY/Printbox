package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

class PrintDocument(
        @SerializedName("pk")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("created_at")
        var createdAt: DateTime,
        @SerializedName("pdf_url")
        var pdfUrl: String,
        @SerializedName("status_name")
        var statusName: String,
        @SerializedName("status")
        var status: Int,
        @SerializedName("color_gs")
        var colorPercent: Float,
        @SerializedName("pages_count")
        var pagesCount: Int) {
    constructor() : this(0, "", DateTime(), "",
            "", 0, 0.0f, 0)
}