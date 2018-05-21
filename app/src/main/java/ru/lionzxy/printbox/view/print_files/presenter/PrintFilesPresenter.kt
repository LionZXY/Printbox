package ru.lionzxy.printbox.view.print_files.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.view.print_files.ui.IPrintFilesView

@InjectViewState
class PrintFilesPresenter : MvpPresenter<IPrintFilesView>() {
    private val disposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}