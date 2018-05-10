package ru.lionzxy.printbox.interactor.auth

import io.reactivex.Completable

interface IAuthInteractor {
    fun login(login: String, password: String): Completable
    fun register(login: String, email: String, password: String, passwordRepeat: String): Completable

}