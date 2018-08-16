package ru.lionzxy.printbox.view.pay.presenter

import android.content.Context
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.di.pay.PayModule
import ru.lionzxy.printbox.interactor.pay.IPayInteractor
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.pay.ui.IPayView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class PayPresenter : MvpPresenter<IPayView>() {
    private val disposable = CompositeDisposable()
    @Inject
    lateinit var cookieJar: ClearableCookieJar
    @Inject
    lateinit var interactor: IPayInteractor

    init {
        App.appComponent.plus(PayModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onFirstLoad()
    }

    fun requestAndOpenLink(sum: Int) {
        viewState.showLoading(true)
        disposable.addAll(interactor.requestOrderPay(sum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.openUrl(it.payUrl)
                }, {
                    Timber.e(it)
                    viewState.showLoading(false)
                }))
    }

    fun loadCookie(context: Context) {
        if (cookieJar !is PersistentCookieJar) {
            return
        }
        val jar = cookieJar as PersistentCookieJar
        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()
        jar.saveInCookieManager(cookieManager)
    }

    fun processURL(url: String) {
        if (url.startsWith(Constants.PAY_FINISH_URL)
                || url.startsWith(Constants.PAY_FINISH_URL_ALTERNATIVE)) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}