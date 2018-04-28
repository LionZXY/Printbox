package ru.lionzxy.printbox.view.print_select.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.view.print_select.ui.IPrintSelectView

@InjectViewState
class PrintSelectPresenter : MvpPresenter<IPrintSelectView>() {
    private val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}