package ru.lionzxy.printbox.view.print_history.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.view.print_history.ui.IPrintHistoryView

@InjectViewState
class PrintHistoryPresenter : MvpPresenter<IPrintHistoryView>() {
    private val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}