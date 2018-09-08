package ru.lionzxy.printbox.data.exceptions

import com.google.gson.annotations.SerializedName
import java.lang.RuntimeException

class NetworkException(var exceptionCode: Int,
                       @SerializedName("detail")
                       var details: String?) : RuntimeException() {
    constructor() : this(0, "")
}