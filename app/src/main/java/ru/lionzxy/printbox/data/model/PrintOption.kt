package ru.lionzxy.printbox.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PrintOption(
        @SerializedName("pk")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("category")
        var category: String,
        @SerializedName("get_category_display")
        var categoryName: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    constructor() : this(0, "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(category)
        parcel.writeString(categoryName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PrintOption> {
        override fun createFromParcel(parcel: Parcel): PrintOption {
            return PrintOption(parcel)
        }

        override fun newArray(size: Int): Array<PrintOption?> {
            return arrayOfNulls(size)
        }
    }
}