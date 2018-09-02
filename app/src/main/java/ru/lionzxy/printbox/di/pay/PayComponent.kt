package ru.lionzxy.printbox.di.pay

import dagger.Subcomponent
import ru.lionzxy.printbox.view.customview.ui.BalanceView
import ru.lionzxy.printbox.view.pay.presenter.PayPresenter

@Subcomponent(
        modules = [PayModule::class]
)
@PayScope
interface PayComponent {
    fun inject(presenter: PayPresenter)
    fun inject(presenter: BalanceView)
}
