package ru.lionzxy.printbox.view.vk.view

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.webkit.*
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_vkauth.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.lionzxy.printbox.view.print.ui.PrintFragment
import ru.lionzxy.printbox.view.vk.presenter.LoginVkPresenter


class LoginVkActivity : MvpAppCompatActivity(), LoginVkView {

    @InjectPresenter
    lateinit var presenter: LoginVkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vkauth)

        clearAll()
        webView.loadUrl(Constants.VKFLOW_URL)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (url == null) {
                    return
                }

                if (url.startsWith(Constants.BASE_URL)) {
                    presenter.onUrlFetch()
                }
            }
        }
    }

    private fun clearAll() {
        webView.clearCache(true)
        deleteDatabase("webview.db")
        deleteDatabase("webviewCache.db")
        CookieSyncManager.createInstance(this)
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
    }

    override fun onSucsLogin() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().putBoolean(Constants.PREFERENCE_FIRSTRUN, false).apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onError() {
        showWebview(Constants.VKFLOW_URL)
        toast(R.string.auth_vk_fail)
    }

    override fun hideWebview() {
        progressBar.visibility = View.VISIBLE
        webView.visibility = View.GONE
    }

    override fun showWebview(url: String) {
        webView.loadUrl(url)
        progressBar.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}