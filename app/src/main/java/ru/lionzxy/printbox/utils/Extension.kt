package ru.lionzxy.printbox.utils

import android.content.Context
import android.widget.Toast
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLDecoder

@Throws(UnsupportedEncodingException::class)
fun URL.splitQuery(): Map<String, String> {
    val queryPairs = LinkedHashMap<String, String>()
    val query = this.query
    val pairs = query.split("&".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
    for (pair in pairs) {
        val idx = pair.indexOf("=")
        queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"))
    }
    return queryPairs
}

fun Context.toast(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}