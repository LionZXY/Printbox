package ru.lionzxy.printbox.di.auth

import dagger.Module
import dagger.Provides
import ru.lionzxy.printbox.interactor.auth.AuthInteractor
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.repository.auth.AuthRepository
import ru.lionzxy.printbox.repository.auth.IAuthRepository

@Module
class AuthModule() {

    @AuthScope
    @Provides
    fun provideRepository(): IAuthRepository {
        return AuthRepository()
    }

    @AuthScope
    @Provides
    fun provideInteractor(repository: IAuthRepository): IAuthInteractor {
        return AuthInteractor(repository)
    }
}
