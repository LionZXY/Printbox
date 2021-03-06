package ru.lionzxy.printbox.di.print

import dagger.Subcomponent
import ru.lionzxy.printbox.view.print.presenter.PrintPresenter
import ru.lionzxy.printbox.view.print_map.presenter.PrintMapPresenter
import ru.lionzxy.printbox.view.print_select.presenter.PrintSelectPresenter

@Subcomponent(
        modules = [PrintModule::class]
)
@PrintScope
interface PrintComponent {
    fun inject(presenter: PrintMapPresenter)
    fun inject(presenter: PrintPresenter)
    fun inject(presenter: PrintSelectPresenter)
}
