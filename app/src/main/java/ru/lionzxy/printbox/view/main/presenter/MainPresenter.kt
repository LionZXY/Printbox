package ru.lionzxy.printbox.view.main.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.main.ui.IMainView
import timber.log.Timber
import java.sql.Timestamp
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<IMainView>() {
    @Inject
    lateinit var interactor: IAuthInteractor
    private val disposable = CompositeDisposable()
    private var lastClickTimeStamp: Timestamp? = null

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initDrawer(interactor.getUser())
    }

    fun onClickBack() {
        val currentTime = Timestamp(System.currentTimeMillis())
        val oldTime = lastClickTimeStamp
        if (oldTime == null) {
            lastClickTimeStamp = currentTime
            viewState.showOnBackToast()
            return
        }

        if (currentTime.time - oldTime.time > Constants.BACKPRESS_TIMEOUT) {
            lastClickTimeStamp = currentTime
            viewState.showOnBackToast()
            return
        }

        viewState.backPressForce()
    }

    fun logout() {
        viewState.showProgressBar(true)
        disposable.addAll(interactor
                .logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showProgressBar(false)
                    Timber.i("Logout from app")
                }, {
                    viewState.showProgressBar(false)
                    Timber.e(it)
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}