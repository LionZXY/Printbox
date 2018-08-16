package ru.lionzxy.printbox.di.pay

import android.content.SharedPreferences
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.di.auth.AuthScope
import ru.lionzxy.printbox.interactor.pay.PayInteractor
import ru.lionzxy.printbox.interactor.pay.IPayInteractor
import ru.lionzxy.printbox.repository.auth.AuthRepository
import ru.lionzxy.printbox.repository.auth.IAuthRepository
import ru.lionzxy.printbox.repository.pay.PayRepository
import ru.lionzxy.printbox.repository.pay.IPayRepository

@Module
class PayModule {

    @PayScope
    @Provides
    fun provideRepository(retrofit: Retrofit): IPayRepository {
        return PayRepository(retrofit)
    }

    @PayScope
    @Provides
    fun provideInteractor(repository: IPayRepository, authRepository: IAuthRepository): IPayInteractor {
        return PayInteractor(repository, authRepository)
    }

    @PayScope
    @Provides
    fun provideAuthRepository(retrofit: Retrofit,
                          preferences: SharedPreferences, gson: Gson,
                          cookieJar: ClearableCookieJar): IAuthRepository {
        return AuthRepository(retrofit, preferences, gson, cookieJar)
    }
}
