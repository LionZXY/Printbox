package ru.lionzxy.printbox.interactor.auth

import io.reactivex.Completable
import ru.lionzxy.printbox.repository.auth.IAuthRepository

class AuthInteractor(
        val authRepository: IAuthRepository
) : IAuthInteractor {
    override fun login(login: String, password: String): Completable {
        return authRepository.login(login, password)
    }

    override fun register(login: String, email: String, password: String, passwordRepeat: String): Completable {
        return authRepository.register(login, email, password, passwordRepeat)
    }

    override fun vklogin(params: Map<String, String>): Completable {
        return authRepository.vklogin(params)
    }
}