package ru.lionzxy.printbox.view.auth.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.auth.ui.IAuthView
import javax.inject.Inject

/**
 * @author Nikita Kulikov <nikita@kulikof.ru>
 * @project SecretBook
 * @date 06.03.18
 */

//TODO save login and password here
@InjectViewState
class AuthPresenter : MvpPresenter<IAuthView>() {
    @Inject
    lateinit var authInteractor: IAuthInteractor
    private val disposable = CompositeDisposable()
    private var hasLoginError = false
    private var hasPasswordError = false

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    fun onClickLogin(login: String, password: String) {
        if (!validateLoginAndPassword(login, password)) {
            return
        }
        viewState.onLoading(true)
        disposable.addAll(authInteractor.login(login, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.onAuth()
                    viewState.onLoading(false)
                }, {
                    viewState.onError(R.string.auth_activity_auth_error)
                    viewState.onLoading(false)
                }))
    }

    fun onChangeLoginOrPassword(login: String, password: String) {
        onChangeLogin(login)
        onChangePassword(password)
        viewState.buttonActive(validateLoginAndPassword(login, password, true))
    }

    private fun onChangeLogin(login: String) {
        if (!hasLoginError) {
            return
        }

        validateLogin(login)
    }

    private fun onChangePassword(password: String) {
        if (!hasPasswordError) {
            return
        }

        validatePassword(password)
    }

    private fun validateLogin(login: String, silent: Boolean = false): Boolean {

        if (login.isEmpty()) {
            if (!silent) {
                hasLoginError = true
                viewState.showLoginError(R.string.auth_activity_login_empty)
            }
            return false
        }
        if (!Constants.LOGIN_PATTERN.matcher(login).matches()) {
            if (!silent) {
                hasLoginError = true
                viewState.showLoginError(R.string.auth_activity_login_matcher)
            }
            return false
        }

        if (!silent) {
            viewState.hideLoginError()
        }

        hasLoginError = false
        return true
    }

    private fun validatePassword(password: String, silent: Boolean = false): Boolean {
        if (password.isEmpty()) {
            if (!silent) {
                hasPasswordError = true
                viewState.showPasswordError(R.string.auth_activity_password_empty)
            }
            return false
        }
        if (password.length < Constants.PASSWORD_MIN_LENGHT) {
            if (!silent) {
                hasPasswordError = true
                viewState.showPasswordError(R.string.auth_activity_password_matcher)
            }
            return false
        }
        if (!silent) {
            viewState.hidePasswordError()
        }
        hasPasswordError = false
        return true
    }

    private fun validateLoginAndPassword(login: String, password: String, silent: Boolean = false): Boolean {
        val valid = validateLogin(login, silent)
        return validatePassword(password, silent) && valid
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}