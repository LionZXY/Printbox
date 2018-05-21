package ru.lionzxy.printbox.di.auth

import dagger.Subcomponent
import ru.lionzxy.printbox.view.auth.presenter.AuthPresenter
import ru.lionzxy.printbox.view.main.presenter.MainPresenter
import ru.lionzxy.printbox.view.print.presenter.PrintPresenter
import ru.lionzxy.printbox.view.register.presenter.RegisterPresenter
import ru.lionzxy.printbox.view.vk.presenter.LoginVkPresenter

@Subcomponent(
        modules = [AuthModule::class]
)
@AuthScope
interface AuthComponent {
    fun inject(presenter: LoginVkPresenter)
    fun inject(presenter: AuthPresenter)
    fun inject(presenter: RegisterPresenter)
    fun inject(presenter: MainPresenter)
}
