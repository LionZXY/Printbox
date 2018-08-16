package ru.lionzxy.printbox.repository.auth

import android.content.SharedPreferences
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.Cookie
import okhttp3.HttpUrl
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

    override fun register(email: String, password: String): Completable {
        return authApi.register(email, password)
                .andThen(updateFromServerUser())
                .subscribeOn(Schedulers.io())
    }

    override fun setAuthCookie(sessionid: String) {
        val url = HttpUrl.parse(Constants.BASE_URL)!!
        val cookie = Cookie.Builder().domain("printbox.io")
                .name(Constants.COOKIE_SESSION)
                .path("/")
                .value(sessionid)
                .build()
        cookieJar.saveFromResponse(url, listOf(cookie))
    }

    override fun setLastUser(user: User): Completable {
        return Completable.fromCallable {
            preferences.edit().putString(PREF_USER, gson.toJson(user)).apply()
        }
    }

    override fun getLastUser(): User {
        val usrJson = preferences.getString(PREF_USER, "{}")
        return gson.fromJson(usrJson, User::class.java)
    }

    override fun getUserAsync(): Single<User> {
        return authApi.currentUser()
                .map { it.first() }
                .doOnSuccess { setLastUser(it) }
                .observeOn(Schedulers.io())
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
            setLastUser(it.first())
        }.subscribeOn(Schedulers.io())
    }
}