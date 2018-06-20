package ru.lionzxy.printbox.view.print_history.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.di.history.HistoryModule
import ru.lionzxy.printbox.interactor.history.IHistoryInteractor
import ru.lionzxy.printbox.view.print_history.ui.IPrintHistoryView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class PrintHistoryPresenter : MvpPresenter<IPrintHistoryView>() {
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var interactor: IHistoryInteractor

    init {
        App.appComponent.plus(HistoryModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadHistory()
    }

    fun loadHistory(silent: Boolean = false) {
        if (!silent) {
            viewState.showLoad(true)
        }
        disposable . addAll (interactor.getHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.loadHistory(it)
                    viewState.showLoad(false)
                }, {
                    viewState.onError(R.string.history_request_error)
                    Timber.e(it)
                    viewState.showLoad(false)
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}