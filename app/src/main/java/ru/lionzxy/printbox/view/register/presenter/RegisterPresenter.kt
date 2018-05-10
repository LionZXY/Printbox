package ru.lionzxy.printbox.view.register.presenter

import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.di.auth.AuthModule
import ru.lionzxy.printbox.interactor.auth.IAuthInteractor
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.register.ui.IRegisterView
import javax.inject.Inject

/**
 * @author Nikita Kulikov <nikita@kulikof.ru>
 * @project SecretBook
 * @date 06.03.18
 */

//TODO save login and password here
@InjectViewState
class RegisterPresenter : MvpPresenter<IRegisterView>() {
    @Inject
    lateinit var authInteractor: IAuthInteractor
    private val disposable = CompositeDisposable()
    private var hasLoginError = false
    private var hasPasswordError = false
    private var hasSecondPasswordError = false
    private var hasEmailError = false

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    fun onClickRegister(login: String, password: String, email: String, passwordRepeat: String) {
        if (!validateLoginAndPassword(login, password, email, passwordRepeat)) {
            return
        }
        viewState.onLoading(true)
        disposable.addAll(authInteractor.register(login, email, password, passwordRepeat)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.onAuth()
                    viewState.onLoading(false)
                }, {
                    viewState.onError(R.string.auth_activity_register_error)
                    viewState.onLoading(false)
                }))
    }

    fun onChangeLoginPasswordEmail(login: String, password: String, email: String, passwordRepeat: String) {
        onChangeLogin(login)
        onChangePassword(password)
        onChangeEmail(email)
        onChangeRepeatePassword(password, passwordRepeat)
        viewState.buttonActive(validateLoginAndPassword(login, password, email, passwordRepeat, true))
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

    private fun onChangeEmail(email: String) {
        if (!hasEmailError) {
            return
        }

        validateEmail(email)
    }

    private fun onChangeRepeatePassword(password: String, passwordRepeat: String) {
        if (!hasSecondPasswordError) {
            return
        }

        validateRepeatPassword(password, passwordRepeat)
    }

    private fun validateRepeatPassword(password: String, passwordRepeat: String, silent: Boolean = false): Boolean {
        if (passwordRepeat.isEmpty()) {
            if (!silent) {
                hasSecondPasswordError = true
                viewState.showSecondPasswordError(R.string.auth_activity_password_empty)
            }
            return false
        }
        if (password != passwordRepeat) {
            if (!silent) {
                hasSecondPasswordError = true
                viewState.showSecondPasswordError(R.string.auth_activity_password_match_repeat)
            }
            return false
        }
        if (!silent) {
            viewState.hideSecondPasswordError()
        }
        return true
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

    private fun validateEmail(email: String, silent: Boolean = false): Boolean {
        if (email.isEmpty()) {
            if (!silent) {
                hasEmailError = true
                viewState.showEmailError(R.string.auth_activity_email_empty)
            }
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!silent) {
                hasEmailError = true
                viewState.showEmailError(R.string.auth_activity_email_matcher)
            }
            return false
        }
        if (!silent) {
            viewState.hideEmailError()
        }
        hasEmailError = false
        return true
    }

    private fun validateLoginAndPassword(login: String, password: String, email: String, passwordRepeat: String, silent: Boolean = false): Boolean {
        var valid = validateLogin(login, silent)
        valid = validatePassword(password, silent) && valid
        valid = validateRepeatPassword(password, passwordRepeat, silent) && valid
        return validateEmail(email, silent) && valid
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}