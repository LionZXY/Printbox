package ru.lionzxy.printbox.repository.auth

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.AuthApi

class AuthRepository(retrofit: Retrofit) : IAuthRepository {
    val authApi = retrofit.create(AuthApi::class.java)

    override fun login(login: String, password: String): Completable {
        return authApi.login(login, password)
                .subscribeOn(Schedulers.io())
    }

    override fun register(login: String, email: String, password: String, passwordRepeat: String): Completable {
        return authApi.register(login, email, password, passwordRepeat)
                .subscribeOn(Schedulers.io())
    }

    override fun vklogin(params: Map<String, String>): Completable {
        return authApi.vkLogin(params)
                .subscribeOn(Schedulers.io())
    }
}