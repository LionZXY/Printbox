package ru.lionzxy.printbox.view.pay.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPayView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun openUrl(url: String)
    @StateStrategyType(SkipStrategy::class)
    fun onFirstLoad()
    fun onFinish(currentBalance: Double)

    fun showLoading(visible: Boolean)
}
