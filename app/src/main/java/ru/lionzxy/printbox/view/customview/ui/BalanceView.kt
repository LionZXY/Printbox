package ru.lionzxy.printbox.view.customview.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.di.pay.PayModule
import ru.lionzxy.printbox.interactor.pay.IPayInteractor
import javax.inject.Inject

class BalanceView : TextView {
    @Inject
    lateinit var interactor: IPayInteractor
    private var balanceObservable: Disposable? = null
    private var balanceRefreshObservable: Disposable? = null

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    init {
        App.appComponent.plus(PayModule()).inject(this)
        balanceRefreshObservable = interactor.getCurrentBalance()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        balanceObservable = interactor.getBalanceObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    text = resources.getString(R.string.view_balance, it)
                }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        balanceObservable?.dispose()
        balanceRefreshObservable?.dispose()
    }
}