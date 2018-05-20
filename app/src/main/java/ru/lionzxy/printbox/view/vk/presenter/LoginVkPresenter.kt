package ru.lionzxy.printbox.view.vk.presenter

import android.webkit.CookieManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.vk.view.LoginVkView
import javax.inject.Inject

@InjectViewState
class LoginVkPresenter : MvpPresenter<LoginVkView>() {
    @Inject
    lateinit var interactor: IAuthInteractor

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    fun onUrlFetch() {
        val sessionid = getCookie(Constants.BASE_URL, Constants.COOKIE_SESSION) ?: return
        if (sessionid.isEmpty()) {
            return
        }
        interactor.setAuthCookie(sessionid)
        viewState.onSucsLogin()
    }

    private fun getCookie(siteName: String, cookieName: String): String? {
        var cookieValue: String? = null

        val cookieManager = CookieManager.getInstance()
        val cookies = cookieManager.getCookie(siteName)
        val temp = cookies.split(";").dropLastWhile({ it.isEmpty() }).map { it.trim() }
        for (ar1 in temp) {
            if (ar1.startsWith(cookieName)) {
                val temp1 = ar1.split("=").dropLastWhile({ it.isEmpty() })
                cookieValue = temp1[1]
                break
            }
        }
        return cookieValue
    }
}