package ru.lionzxy.printbox.utils.auth

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import ru.lionzxy.printbox.utils.Constants
import java.io.IOException

class AddSessionCookieInterceptor(private val preferences: SharedPreferences) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val sessionid = preferences.getString(Constants.PREFERENCE_SESSIONID, "")
        builder.addHeader(Constants.HEADER_COOKIE, "${Constants.COOKIE_SESSIONID}=$sessionid")
        return chain.proceed(builder.build())
    }
}