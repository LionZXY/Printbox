package ru.lionzxy.printbox.interactor.auth

import io.reactivex.Completable
import ru.lionzxy.printbox.data.model.User
import ru.lionzxy.printbox.repository.auth.IAuthRepository

class AuthInteractor(
        val authRepository: IAuthRepository
) : IAuthInteractor {
    override fun login(login: String, password: String): Completable {
        return authRepository.login(login, password)
    }

    override fun register(email: String, password: String, passwordRepeat: String): Completable {
        return authRepository.register(email, password)
    }

    override fun setAuthCookie(sessionid: String) {
        return authRepository.setAuthCookie(sessionid)
    }

    override fun getUser(): User {
        return authRepository.getUser()
    }

    override fun logout(): Completable {
        return authRepository.logout()
    }
}