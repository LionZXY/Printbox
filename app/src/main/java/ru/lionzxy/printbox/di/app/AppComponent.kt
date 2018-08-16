package ru.lionzxy.printbox.di.app

import dagger.Component
import ru.lionzxy.printbox.di.auth.AuthComponent
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.di.files.FilesComponent
import ru.lionzxy.printbox.di.files.FilesModule
import ru.lionzxy.printbox.di.history.HistoryComponent
import ru.lionzxy.printbox.di.history.HistoryModule
import ru.lionzxy.printbox.di.pay.PayComponent
import ru.lionzxy.printbox.di.pay.PayModule
import ru.lionzxy.printbox.di.print.PrintComponent
import ru.lionzxy.printbox.di.print.PrintModule
import ru.lionzxy.printbox.view.pay.presenter.PayPresenter
import javax.inject.Singleton

@Component(modules = [AppModule::class, CartModule::class, DataBaseModule::class])
@Singleton
interface AppComponent {
    fun plus(module: AuthModule): AuthComponent
    fun plus(module: PrintModule): PrintComponent
    fun plus(module: FilesModule): FilesComponent
    fun plus(module: HistoryModule): HistoryComponent
    fun plus(module: PayModule): PayComponent
}