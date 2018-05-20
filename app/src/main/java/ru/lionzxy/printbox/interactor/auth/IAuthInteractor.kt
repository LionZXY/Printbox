package ru.lionzxy.printbox.interactor.auth

import io.reactivex.Completable
import ru.lionzxy.printbox.data.model.User

interface IAuthInteractor {
    fun login(login: String, password: String): Completable
    fun register(login: String, email: String, password: String, passwordRepeat: String): Completable
    fun setAuthCookie(sessionid: String)
    fun getUser(): User
    fun logout(): Completable
}