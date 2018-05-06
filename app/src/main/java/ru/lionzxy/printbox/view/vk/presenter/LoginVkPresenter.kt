package ru.lionzxy.printbox.view.vk.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.view.vk.view.LoginVkView
import javax.inject.Inject

@InjectViewState
class LoginVkPresenter : MvpPresenter<LoginVkView>() {
    @Inject
    lateinit var interactor: IAuthInteractor

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    fun onCodeRecieve(code: String) {
        viewState.hideWebview()
        /*interactor.auth(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    router.exitWithMessage(code)
                }, Timber::e)*/
    }
}