package ru.lionzxy.printbox.view.auth.ui

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_auth.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.auth.presenter.AuthPresenter

/**
 * @author Nikita Kulikov <nikita@kulikof.ru>
 * @project SecretBook
 * @date 06.03.18
 */

class AuthActivity : MvpAppCompatActivity(), IAuthView {
    @InjectPresenter
    lateinit var authPresenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_auth)

        buttonLogin.setOnClickListener {
            authPresenter.onClickLogin(editTextLogin.text.toString(),
                    editTextPassword.text.toString())
        }
        RxTextView.textChanges(editTextLogin).subscribe {
            authPresenter.onChangeLoginOrPassword(it.toString(),
                    editTextPassword.text.toString())
        }
        RxTextView.textChanges(editTextPassword).subscribe {
            authPresenter.onChangeLoginOrPassword(editTextLogin.text.toString(),
                    it.toString())
        }
    }

    override fun onLoading(isEnable: Boolean) {
        progressLogin.visibility = if (isEnable) View.VISIBLE else View.GONE
        buttonLogin.visibility = if (isEnable) View.GONE else View.VISIBLE
    }

    override fun onError(test: String) {
        toast(test)
    }

    override fun showLoginError(resError: Int) {
        errorLoginText.visibility = View.VISIBLE
        errorLoginText.text = getString(resError)
    }

    override fun showPasswordError(resError: Int) {
        errorPasswordText.visibility = View.VISIBLE
        errorPasswordText.text = getText(resError)
    }

    override fun hideLoginError() {
        errorLoginText.visibility = View.GONE
    }

    override fun hidePasswordError() {
        errorPasswordText.visibility = View.GONE
    }

    override fun buttonActive(active: Boolean) {
        buttonLogin.background = if (active) ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_active, theme)
        else ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_disactive, theme)
    }
}