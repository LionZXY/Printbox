package ru.lionzxy.printbox.view.print_files.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.di.files.FilesModule
import ru.lionzxy.printbox.interactor.files.IFilesInteractor
import ru.lionzxy.printbox.view.print_files.ui.IPrintFilesView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class PrintFilesPresenter : MvpPresenter<IPrintFilesView>() {
    private val disposable = CompositeDisposable()
    @Inject

    lateinit var interactor: IFilesInteractor

    init {
        App.appComponent.plus(FilesModule()).inject(this)
    }

    fun loadList() {
        viewState.showLoading(true)
        disposable.addAll(interactor.getUserFiles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showLoading(false)
                    viewState.setFiles(it)
                }, {
                    Timber.e(it)
                    viewState.onError(R.string.files_update_error)
                    viewState.showLoading(false)
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}