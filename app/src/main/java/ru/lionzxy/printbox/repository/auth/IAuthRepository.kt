package ru.lionzxy.printbox.repository.auth

import io.reactivex.Completable
import ru.lionzxy.printbox.data.model.User

interface IAuthRepository {
    fun login(login: String, password: String): Completable
    fun register(email: String, password: String): Completable
    fun setAuthCookie(sessionid: String)
    fun setUser(user: User): Completable
    fun getUser(): User
    fun logout(): Completable
}