package ru.lionzxy.printbox.view.main.presenter

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.navigation.PrintBoxRouter
import ru.lionzxy.printbox.utils.navigation.ScreenKey
import ru.lionzxy.printbox.view.main.ui.IMainView
import ru.lionzxy.printbox.view.main.ui.MainActivity
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
        viewState.openFragmentWithId(MainActivity.ID_PRINTFRAGMENT)
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

    fun openScreen(screenKey: String, data: Bundle?) { //TODO: transfer screenkey in target fragment
        if (screenKey.startsWith(PrintBoxRouter.MAIN_PRINT)) {
            viewState.openFragmentWithId(MainActivity.ID_PRINTFRAGMENT, data)
            return
        }

        if (screenKey.startsWith(PrintBoxRouter.MAIN_PAY)) {
            viewState.openFragmentWithId(MainActivity.ID_PAYFRAGMENT, data)
            return
        }
    }

    fun onClickDrawer(indentifier: Long): Boolean {
        when (indentifier) {
            MainActivity.ID_PRINTFRAGMENT -> viewState.openFragmentWithId(MainActivity.ID_PRINTFRAGMENT)
            MainActivity.ID_PAYFRAGMENT -> viewState.openFragmentWithId(MainActivity.ID_PAYFRAGMENT)
            MainActivity.ID_LOGOUT -> logout()
        }
        return true
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