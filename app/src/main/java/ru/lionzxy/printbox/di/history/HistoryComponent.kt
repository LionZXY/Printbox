package ru.lionzxy.printbox.di.history

import dagger.Subcomponent
import ru.lionzxy.printbox.view.print_files.presenter.PrintFilesPresenter
import ru.lionzxy.printbox.view.print_history.presenter.PrintHistoryPresenter
import ru.lionzxy.printbox.view.print_history.ui.HistoryViewHolder

@Subcomponent(
        modules = [HistoryModule::class]
)
@HistoryScope
interface HistoryComponent {
    fun inject(presenter: PrintHistoryPresenter)
    fun inject(presenter: HistoryViewHolder)
}
