package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

class PrintHistory(
        @SerializedName("id")
        var id: Int,
        @SerializedName("created_at")
        var createdAt: DateTime,
        @SerializedName("operation_owner")
        var owner: String,
        @SerializedName("price")
        var price: Int,
        @SerializedName("count_completed")
        var countCompleted: Int,
        @SerializedName("count_total")
        var countTotal: Int,
        @SerializedName("operation_printer")
        var operationPrinter: Int,
        @SerializedName("status")
        var status: Int,
        @SerializedName("status_name")
        var statusName: String,
        @SerializedName("printer")
        var printerName: String,
        @SerializedName("color_option")
        var colorOption: PrintOption,
        @SerializedName("double_page_option")
        var duplexOption: PrintOption,
        @SerializedName("document")
        var document: PrintDocument) {
    constructor() : this(0, DateTime(), "", 0, 0, 0,
            0, 0, "", "", PrintOption(),
            PrintOption(), PrintDocument())
}