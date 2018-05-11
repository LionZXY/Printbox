package ru.lionzxy.printbox.view.vk.view

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_vkauth.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.splitQuery
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.print.ui.PrintActivity
import ru.lionzxy.printbox.view.vk.presenter.LoginVkPresenter
import timber.log.Timber
import java.net.URL
import android.webkit.CookieSyncManager



class LoginVkActivity : MvpAppCompatActivity(), LoginVkView {

    @InjectPresenter
    lateinit var presenter: LoginVkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vkauth)

        webView.loadUrl(Constants.VKFLOW_URL)
        webView.clearCache(true)
        deleteDatabase("webview.db")
        deleteDatabase("webviewCache.db")
        CookieSyncManager.createInstance(this)
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
        webView.webViewClient = object : WebViewClient() {
            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url == null) {
                    return false
                }
                if (url.startsWith(Constants.REDIR_URL)) {
                    val params = URL(url).splitQuery()
                    presenter.onVkLogin(params)
                } else {
                    view.loadUrl(url)
                }
                return true
            }
        }
    }

    override fun onSucsLogin() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().putBoolean(Constants.PREFERENCE_FIRSTRUN, false).apply()

        val intent = Intent(this, PrintActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
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