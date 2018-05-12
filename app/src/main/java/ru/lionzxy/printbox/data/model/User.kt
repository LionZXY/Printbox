package ru.lionzxy.printbox.data.model

import com.google.gson.annotations.SerializedName

class User(@SerializedName("pk")
           var pk: Int,
           @SerializedName("balance")
           var balance: Int,
           @SerializedName("bonus_balance")
           var bonusBalance: Int,
           @SerializedName("real_balance")
           var realBalance: Int,
           @SerializedName("email")
           var email: String,
           @SerializedName("username")
           var username: String,
           @SerializedName("first_name")
           var firstName: String,
           @SerializedName("last_name")
           var lastName: String,
           @SerializedName("last_place")
           var lastPlace: Int,
           @SerializedName("last_place_detail")
           var lastPlaceDetail: Place,
           @SerializedName("last_duplex_option")
           var lDuplexOption: Int,
           @SerializedName("last_color_option")
           var lColorOption: Int,
           @SerializedName("last_printer_option")
           var lPrintedOption: Int) {
    constructor() : this(0, 0, 0,
            0, "", "",
            "", "", 0,
            Place(), 0, 0, 0)
}