package ru.lionzxy.printbox.utils.govnofix

import okhttp3.Interceptor
import okhttp3.Response

class FuckBackEndInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var cookie = request.headers().get("cookie") ?: "a=a"
        cookie += "; csrftoken=FUCKBACKEND"

        val newRequest = request.newBuilder()
                .addHeader("cookie", cookie)
                .addHeader("X-CSRFToken", "FUCKBACKEND")
                .build()

        return chain.proceed(newRequest)
    }
}