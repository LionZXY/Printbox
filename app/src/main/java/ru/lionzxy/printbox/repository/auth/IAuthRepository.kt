package ru.lionzxy.printbox.repository.auth

import io.reactivex.Completable

interface IAuthRepository {
    fun login(login: String, password: String): Completable
    fun register(login: String, email: String, password: String, passwordRepeat: String): Completable
}