package ru.lionzxy.printbox.repository.auth

import io.reactivex.Completable
import ru.lionzxy.printbox.data.model.User

interface IAuthRepository {
    fun login(login: String, password: String): Completable
    fun register(login: String, email: String, password: String, passwordRepeat: String): Completable
    fun vklogin(params: Map<String, String>): Completable
    fun setUser(user: User): Completable
    fun getUser(): User
    fun logout(): Completable
}