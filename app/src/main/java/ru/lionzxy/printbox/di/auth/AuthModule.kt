package ru.lionzxy.printbox.di.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.interactor.auth.AuthInteractor
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.repository.auth.AuthRepository
import ru.lionzxy.printbox.repository.auth.IAuthRepository

@Module
class AuthModule() {

    @AuthScope
    @Provides
    fun provideRepository(retrofit: Retrofit): IAuthRepository {
        return AuthRepository(retrofit)
    }

    @AuthScope
    @Provides
    fun provideInteractor(repository: IAuthRepository): IAuthInteractor {
        return AuthInteractor(repository)
    }
}
