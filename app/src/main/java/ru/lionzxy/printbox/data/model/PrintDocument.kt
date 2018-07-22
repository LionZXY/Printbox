package ru.lionzxy.printbox.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

@Entity(tableName = PrintDocument.TABLE_NAME)
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
        var pagesCount: Int,
        @SerializedName(TABLE_UPLOAD_ID)
        @PrimaryKey
        var uploadDraftId: String) {
    constructor() : this(0, "", DateTime(), "",
            "", 0, 0.0f, 0, "")

    companion object {
        const val TABLE_NAME = "docs"
        const val TABLE_UPLOAD_ID = "uploadDraftId"

        fun getUploadObject(uploadId: String): PrintDocument {
            val doc = PrintDocument()
            doc.id = -1
            doc.uploadDraftId = uploadId
            return doc
        }
    }

}