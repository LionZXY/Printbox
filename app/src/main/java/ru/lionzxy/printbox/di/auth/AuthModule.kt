package ru.lionzxy.printbox.di.auth

import android.content.SharedPreferences
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.google.gson.Gson
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
    fun provideRepository(retrofit: Retrofit,
                          preferences: SharedPreferences, gson: Gson,
                          cookieJar: ClearableCookieJar): IAuthRepository {
        return AuthRepository(retrofit, preferences, gson, cookieJar)
    }

    @AuthScope
    @Provides
    fun provideInteractor(repository: IAuthRepository): IAuthInteractor {
        return AuthInteractor(repository)
    }
}
