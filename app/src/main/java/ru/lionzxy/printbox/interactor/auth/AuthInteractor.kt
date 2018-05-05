package ru.lionzxy.printbox.interactor.auth

import ru.lionzxy.printbox.repository.auth.IAuthRepository

class AuthInteractor(
        val authRepository: IAuthRepository
) : IAuthInteractor {

}