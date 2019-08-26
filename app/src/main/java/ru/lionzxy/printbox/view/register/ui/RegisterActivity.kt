package ru.lionzxy.printbox.view.register.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_register.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.auth.ui.AuthActivity
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.lionzxy.printbox.view.print.ui.PrintFragment
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
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_register)

        buttonLogin.setOnClickListener {
            registerPresenter.onClickRegister(editTextPassword.text.toString(),
                    editTextEmail.text.toString())
        }
        disposable.add(RxTextView.textChanges(editTextPassword).subscribe {
            registerPresenter.onChangeLoginPasswordEmail(it.toString(),
                    editTextEmail.text.toString())
        })
        disposable.add(RxTextView.textChanges(editTextEmail).subscribe {
            registerPresenter.onChangeLoginPasswordEmail(editTextPassword.text.toString(),
                    it.toString())
        })
        authbutton.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, AuthActivity::class.java))
        }
        vkauth.setOnClickListener { startActivity(Intent(this@RegisterActivity, LoginVkActivity::class.java)) }
    }

    override fun onLoading(isEnable: Boolean) {
        progressLogin.visibility = if (isEnable) View.VISIBLE else View.GONE
        register_field.visibility = if (isEnable) View.GONE else View.VISIBLE
    }

    override fun onError(resId: Int) {
        toast(resId)
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

    override fun hidePasswordError() {
        errorPasswordText.visibility = View.GONE
    }

    override fun buttonActive(active: Boolean) {
        buttonLogin.background = if (active) ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_active, theme)
        else ResourcesCompat.getDrawable(resources, R.drawable.rounded_button_active, theme) //TODO design fix
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose();
    }

    override fun onAuth() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().putBoolean(Constants.PREFERENCE_FIRSTRUN, false).apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}