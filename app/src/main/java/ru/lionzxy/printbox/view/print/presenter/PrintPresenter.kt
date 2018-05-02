package ru.lionzxy.printbox.view.print.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.view.print.ui.IPrintView

@InjectViewState
class PrintPresenter : MvpPresenter<IPrintView>() {
    private val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}