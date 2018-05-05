package ru.lionzxy.printbox.di.app

import dagger.Component
import ru.lionzxy.printbox.di.auth.AuthComponent
import ru.lionzxy.printbox.di.auth.AuthModule
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun plus(module: AuthModule): AuthComponent
}