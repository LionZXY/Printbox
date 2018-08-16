package ru.lionzxy.printbox.repository.auth

import io.reactivex.Completable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.User

interface IAuthRepository {
    fun login(login: String, password: String): Completable
    fun register(email: String, password: String): Completable
    fun setAuthCookie(sessionid: String)
    fun setLastUser(user: User): Completable
    fun getLastUser(): User
    fun getUserAsync(): Single<User>
    fun logout(): Completable
}