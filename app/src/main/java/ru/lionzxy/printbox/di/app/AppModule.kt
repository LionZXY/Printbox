package ru.lionzxy.printbox.di.app

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fatboyindustrial.gsonjodatime.Converters
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import net.gotev.uploadservice.UploadService
import net.gotev.uploadservice.okhttp.OkHttpStack
import okhttp3.Cookie
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.lionzxy.printbox.BuildConfig
import ru.lionzxy.printbox.data.db.AppDatabase
import ru.lionzxy.printbox.utils.auth.Handle401Interceptor
import ru.lionzxy.printbox.utils.govnofix.FuckBackEndInterceptor
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
    fun provideGson(): Gson {
        return Converters.registerDateTime(GsonBuilder()).create()
    }

    @Singleton
    @Provides
    fun provideCookieJar(): ClearableCookieJar {
        val cookiePersistor = SharedPrefsCookiePersistor(context)
        val jar = PersistentCookieJar(SetCookieCache(), cookiePersistor)
        val cookie = Cookie.Builder().name("csrftoken").value("FUCKBACKEND").domain("printbox.io").build()
        cookiePersistor.saveAll(listOf(cookie))
        return jar
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cookieJar: ClearableCookieJar, context: Context): OkHttpClient {
        val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(FuckBackEndInterceptor())
                .addInterceptor(Handle401Interceptor(context))
                .build()
        UploadService.HTTP_STACK = OkHttpStack(client)
        return client
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app").build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}