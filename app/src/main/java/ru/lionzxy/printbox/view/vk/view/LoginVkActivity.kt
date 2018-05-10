package ru.lionzxy.printbox.view.vk.view

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_vkauth.*
import ru.lionzxy.printbox.BuildConfig
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.splitQuery
import ru.lionzxy.printbox.view.vk.presenter.LoginVkPresenter
import java.net.URL

class LoginVkActivity : MvpAppCompatActivity(), LoginVkView {

    @InjectPresenter
    lateinit var presenter: LoginVkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vkauth)

        webView.loadUrl(Constants.VKFLOW_URL)
        webView.clearCache(true)
        webView.webViewClient = object : WebViewClient() {
            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url == null) {
                    return false
                }
                if (url.startsWith("${BuildConfig.API_URL}auth?code=")) {
                    val params = URL(url).splitQuery()
                    val code = params["code"]!!
                    presenter.onCodeRecieve(code)
                } else {
                    view.loadUrl(url)
                }
                return true
            }
        }
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