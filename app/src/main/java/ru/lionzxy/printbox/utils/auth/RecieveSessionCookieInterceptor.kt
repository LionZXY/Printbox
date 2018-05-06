package ru.lionzxy.printbox.utils.auth

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import ru.lionzxy.printbox.utils.Constants
import java.io.IOException


class RecieveSessionCookieInterceptor(private val preference: SharedPreferences) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.headers(Constants.HEADER_SETCOOKIE).isEmpty()) {
            val cookies = HashMap<String, String>()

            for (header in originalResponse.headers(Constants.HEADER_SETCOOKIE)) {
                val headers = header.split(";").map { it.trim() }
                headers.map { it.split("=") }.forEach { cookies[it[0]] = it[1] }
            }
            if (cookies.containsKey(Constants.COOKIE_SESSIONID)) {
                preference.edit()
                        .putString(Constants.PREFERENCE_SESSIONID,
                                cookies[Constants.COOKIE_SESSIONID])
                        .apply()
            }
        }

        return originalResponse
    }
}