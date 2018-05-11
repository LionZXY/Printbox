package ru.lionzxy.printbox.view.vk.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.view.vk.view.LoginVkView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class LoginVkPresenter : MvpPresenter<LoginVkView>() {
    @Inject
    lateinit var interactor: IAuthInteractor

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    fun onVkLogin(params: Map<String, String>) {
        viewState.hideWebview()
        interactor.vklogin(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.onSucsLogin()
                }, {
                    viewState.onError()
                    Timber.e(it)
                })
    }
}