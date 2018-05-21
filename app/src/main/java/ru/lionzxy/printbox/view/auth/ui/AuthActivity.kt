package ru.lionzxy.printbox.view.auth.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_auth.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.auth.presenter.AuthPresenter
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.lionzxy.printbox.view.register.ui.RegisterActivity
import ru.lionzxy.printbox.view.vk.view.LoginVkActivity

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
        registerbutton.setOnClickListener {
            startActivity(Intent(this@AuthActivity, RegisterActivity::class.java))
        }
        vkauth.setOnClickListener { startActivity(Intent(this@AuthActivity, LoginVkActivity::class.java)) }
    }

    override fun onLoading(isEnable: Boolean) {
        progressLogin.visibility = if (isEnable) View.VISIBLE else View.GONE
        auth_field.visibility = if (isEnable) View.GONE else View.VISIBLE
    }

    override fun onError(resId: Int) {
        toast(resId)
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

    override fun onAuth() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().putBoolean(Constants.PREFERENCE_FIRSTRUN, false).apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}