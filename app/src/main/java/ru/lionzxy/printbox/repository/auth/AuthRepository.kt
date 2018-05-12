package ru.lionzxy.printbox.repository.auth

import android.content.SharedPreferences
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import okhttp3.CookieJar
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.AuthApi
import ru.lionzxy.printbox.data.model.User
import ru.lionzxy.printbox.utils.Constants

private const val PREF_USER = "user"

class AuthRepository(retrofit: Retrofit,
                     private val preferences: SharedPreferences,
                     private val gson: Gson,
                     private val cookieJar: ClearableCookieJar) : IAuthRepository {
    val authApi = retrofit.create(AuthApi::class.java)

    override fun login(login: String, password: String): Completable {
        return authApi.login(login, password)
                .andThen(updateFromServerUser())
                .subscribeOn(Schedulers.io())
    }

    override fun register(login: String, email: String, password: String, passwordRepeat: String): Completable {
        return authApi.register(login, email, password, passwordRepeat)
                .andThen(updateFromServerUser())
                .subscribeOn(Schedulers.io())
    }

    override fun vklogin(params: Map<String, String>): Completable {
        return authApi.vkLogin(params)
                .andThen(updateFromServerUser())
                .subscribeOn(Schedulers.io())
    }

    override fun setUser(user: User): Completable {
        return Completable.fromCallable {
            preferences.edit().putString(PREF_USER, gson.toJson(user)).apply()
        }
    }

    override fun getUser(): User {
        val usrJson = preferences.getString(PREF_USER, "{}")
        return gson.fromJson(usrJson, User::class.java)
    }

    override fun logout(): Completable {
        return Completable.fromCallable {
            preferences.edit()
                    .remove(PREF_USER)
                    .apply()
            cookieJar.clear()
        }.andThen(updateFromServerUser())
    }

    private fun updateFromServerUser(): Completable {
        return authApi.currentUser().flatMapCompletable {
            setUser(it.first())
        }.subscribeOn(Schedulers.io())
    }
}