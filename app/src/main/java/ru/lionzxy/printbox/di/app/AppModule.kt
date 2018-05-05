package ru.lionzxy.printbox.di.app

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.lionzxy.printbox.BuildConfig
import ru.lionzxy.printbox.utils.auth.AddSessionCookieInterceptor
import ru.lionzxy.printbox.utils.auth.RecieveSessionCookieInterceptor
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(preferences: SharedPreferences): OkHttpClient {
        val client = OkHttpClient()
        client.interceptors().add(AddSessionCookieInterceptor(preferences))
        client.interceptors().add(RecieveSessionCookieInterceptor(preferences))
        return client
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}