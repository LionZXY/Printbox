package ru.lionzxy.printbox.view.print_map.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.view.print_map.ui.IPrintMapView

@InjectViewState
class PrintMapPresenter : MvpPresenter<IPrintMapView>() {
    private val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}