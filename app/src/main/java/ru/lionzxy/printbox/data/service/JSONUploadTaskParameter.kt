package ru.lionzxy.printbox.data.service

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

const val EXTRA_UPLOAD_TASK = "json_upload_task"

data class JSONUploadTaskParameter(
        var uploadUrl: String = "",
        var uri: Uri = Uri.EMPTY
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Uri::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uploadUrl)
        parcel.writeParcelable(uri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JSONUploadTaskParameter> {
        override fun createFromParcel(parcel: Parcel): JSONUploadTaskParameter {
            return JSONUploadTaskParameter(parcel)
        }

        override fun newArray(size: Int): Array<JSONUploadTaskParameter?> {
            return arrayOfNulls(size)
        }
    }
}