package ru.lionzxy.printbox.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

const val EXTRA_PRINT = "printplace"

class PrintPlace(
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
        var place: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readSerializable() as DateTime,
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.createTypedArrayList(PrintOption),
            parcel.createTypedArrayList(PrintOption),
            parcel.readInt(),
            parcel.readInt()) {
    }

    constructor() : this(0, "", DateTime(), 0, "",
            "", "", "", "", 0.0,
            0.0, emptyList(), emptyList(),
            0, 0)

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (other !is PrintPlace) return false
        return other.id == id
    }

    fun isEmpty() = id == 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeSerializable(createdAt)
        parcel.writeInt(status)
        parcel.writeString(statusName)
        parcel.writeString(imageUrl)
        parcel.writeString(placeDescription)
        parcel.writeString(description)
        parcel.writeString(tags)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeTypedList(optionDoublePage)
        parcel.writeTypedList(optionColor)
        parcel.writeInt(paperCount)
        parcel.writeInt(place)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PrintPlace> {
        override fun createFromParcel(parcel: Parcel): PrintPlace {
            return PrintPlace(parcel)
        }

        override fun newArray(size: Int): Array<PrintPlace?> {
            return arrayOfNulls(size)
        }
    }
}