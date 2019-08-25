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
    private var hasPasswordError = false
    private var hasEmailError = false

    init {
        App.appComponent.plus(AuthModule()).inject(this)
    }

    fun onClickRegister(password: String, email: String) {
        if (!validateLoginAndPassword(password, email)) {
            return
        }
        viewState.onLoading(true)
        disposable.addAll(authInteractor.register(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.onAuth()
                    viewState.onLoading(false)
                }, {
                    viewState.onError(R.string.auth_activity_register_error)
                    viewState.onLoading(false)
                }))
    }

    fun onChangeLoginPasswordEmail(password: String, email: String) {
        onChangePassword(password)
        onChangeEmail(email)
        viewState.buttonActive(validateLoginAndPassword(password, email, true))
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

    private fun validateLoginAndPassword(password: String, email: String, silent: Boolean = false): Boolean {
        val valid =  validatePassword(password, silent)
        return validateEmail(email, silent) && valid
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}