package ru.lionzxy.printbox.view.print_select.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPrintSelectView : MvpView {
    fun openPrintMapSelect()
}
