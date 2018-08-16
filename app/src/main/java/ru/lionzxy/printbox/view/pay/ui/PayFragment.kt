package ru.lionzxy.printbox.view.pay.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_pay.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.view.pay.presenter.PayPresenter
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.lionzxy.printbox.view.main.interfaces.IOnBackDelegator


class PayFragment : MvpAppCompatFragment(), IPayView, IOnBackDelegator {
    @InjectPresenter
    lateinit var payPresenter: PayPresenter

    companion object {
        const val TAG = "pay_fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pay, container, false)
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun openUrl(url: String) {
        payPresenter.loadCookie(context!!)
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    payPresenter.processURL(url)
                }
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progress_bar.isIndeterminate = false
                progress_bar.progress = newProgress
                progress_bar.max = 100

                if (newProgress == 100) {
                    progress_bar.visibility = View.GONE
                }
            }
        }
    }

    override fun onFirstLoad() {
        payPresenter.requestAndOpenLink(11) //TODO
    }

    override fun showLoading(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        webView.visibility = if (visible) View.GONE else View.VISIBLE
    }

    override fun onFinish(currentBalance: Double) {

    }

    override fun onBack(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }
}