package ru.lionzxy.printbox.utils.network

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import ru.lionzxy.printbox.data.exceptions.NetworkException
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.auth.ui.AuthActivity
import timber.log.Timber
import java.net.HttpURLConnection

class NetworkCodeToExceptionInterceptor(val gson: Gson, val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.isSuccessful) {
            var details = NetworkException()
            try {
                details = gson.fromJson(originalResponse.body()!!.string(), NetworkException::class.java)
            } catch (e: Exception) {
                Timber.e(e)
            }
            details.exceptionCode = originalResponse.code()

            if (!details.details.isNullOrEmpty()) {
                context.toast(details.details!!)
            }
            throw details
        }
        return originalResponse
    }
}