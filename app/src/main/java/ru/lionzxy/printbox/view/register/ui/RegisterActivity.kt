package ru.lionzxy.printbox.view.register.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_register.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.auth.ui.AuthActivity
import ru.lionzxy.printbox.view.register.presenter.RegisterPresenter
import ru.lionzxy.printbox.view.vk.view.LoginVkActivity

/**
 * @author Nikita Kulikov <nikita@kulikof.ru>
 * @project SecretBook
 * @date 06.03.18
 */

class RegisterActivity : MvpAppCompatActivity(), IRegisterView {
    @InjectPresenter
    lateinit var registerPresenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_register)

        buttonLogin.setOnClickListener {
            registerPresenter.onClickRegister(editTextLogin.text.toString(),
                    editTextPassword.text.toString(),
                    editTextEmail.text.toString())
        }
        RxTextView.textChanges(editTextLogin).subscribe {
            registerPresenter.onChangeLoginPasswordEmail(it.toString(),
                    editTextPassword.text.toString(),
                    editTextEmail.text.toString())
        }
        RxTextView.textChanges(editTextPassword).subscribe {
            registerPresenter.onChangeLoginPasswordEmail(editTextLogin.text.toString(),
                    it.toString(),
                    editTextEmail.text.toString())
        }
        RxTextView.textChanges(editTextEmail).subscribe {
            registerPresenter.onChangeLoginPasswordEmail(editTextLogin.text.toString(),
                    editTextPassword.text.toString(),
                    it.toString())
        }
        authbutton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        vkauth.setOnClickListener { startActivity(Intent(this@RegisterActivity, LoginVkActivity::class.java)) }
    }

    override fun onLoading(isEnable: Boolean) {
        progressLogin.visibility = if (isEnable) View.VISIBLE else View.GONE
        register_field.visibility = if (isEnable) View.GONE else View.VISIBLE
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

    override fun showEmailError(resError: Int) {
        errorEmailText.visibility = View.VISIBLE
        errorEmailText.text = getText(resError)
    }

    override fun hideEmailError() {
        errorEmailText.visibility = View.GONE
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