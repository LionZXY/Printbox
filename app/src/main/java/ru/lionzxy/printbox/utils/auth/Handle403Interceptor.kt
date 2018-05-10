package ru.lionzxy.printbox.utils.auth

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.auth.ui.AuthActivity
import java.net.HttpURLConnection

class Handle403Interceptor(private val context: Context) : Interceptor {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)!!

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (pref.getBoolean(Constants.PREFERENCE_FIRSTRUN, true)) {
            return originalResponse
        }

        if (originalResponse.code() == HttpURLConnection.HTTP_FORBIDDEN) {
            pref.edit().putBoolean(Constants.PREFERENCE_FIRSTRUN, true).apply()

            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        return originalResponse
    }
}