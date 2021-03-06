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
import ru.lionzxy.printbox.data.stores.IBalanceStore
import ru.lionzxy.printbox.utils.Constants

private const val PREF_USER = "user"
const val PREF_BALANCE = "balance"

class AuthRepository(retrofit: Retrofit,
                     private val preferences: SharedPreferences,
                     private val gson: Gson,
                     private val cookieJar: ClearableCookieJar,
                     private val balanceStore: IBalanceStore) : IAuthRepository {
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
            preferences.edit().putString(PREF_USER, gson.toJson(user))
                    .putInt(PREF_BALANCE, user.balance).apply()
            balanceStore.setBalance(user.balance.toDouble() / 100)
        }
    }

    override fun getLastUser(): User {
        val usrJson = preferences.getString(PREF_USER, "{}")
        return gson.fromJson(usrJson, User::class.java)
    }

    override fun getUserAsync(): Single<User> {
        return authApi.currentUser()
                .map { it.first() }
                .flatMap { setLastUser(it).toSingleDefault(it) }
                .subscribeOn(Schedulers.io())
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
            setUser(it)
        }.subscribeOn(Schedulers.io())
    }
}