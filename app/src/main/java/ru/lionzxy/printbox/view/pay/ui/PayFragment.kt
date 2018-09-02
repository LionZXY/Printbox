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


class PayFragment : MvpAppCompatFragment(), IPayView, IOnBackDelegator, OnRequestPayDialogResult {
    @InjectPresenter
    lateinit var payPresenter: PayPresenter
    var finish = false

    companion object {
        const val TAG = "pay_fragment"
        const val EXTRA_REQUEST_SUM = "request_sum"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        to_main_button.setOnClickListener {
            payPresenter.onPrintScreen()
            finish = false
        }
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
                showLoading(newProgress < 100)
            }
        }
    }

    override fun onFirstLoad() {
        val sum = arguments?.getInt(EXTRA_REQUEST_SUM, 0) ?: 0
        if (sum > 10) {
            payPresenter.requestAndOpenLink(sum)
            return
        }

        RequestPayDialog().setOnRequestPayDialogResult(this)
                .show(activity!!.supportFragmentManager, RequestPayDialog.TAG)
    }

    override fun showLoading(visible: Boolean) {
        if (finish) return
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        finish_pay.visibility = View.GONE
        webView.visibility = if (visible) View.GONE else View.VISIBLE
    }

    override fun onFinish(currentBalance: Double) {
        finish = true
        progress_bar.visibility = View.GONE
        webView.visibility = View.GONE
        finish_pay.visibility = View.VISIBLE
        finish_pay_total.text = getString(R.string.pay_total, currentBalance)
    }

    override fun onCancel() {
        payPresenter.onPrintScreen()
    }

    override fun onResult(balance: Int) {
        payPresenter.requestAndOpenLink(balance)
    }

    override fun onBack(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }
}