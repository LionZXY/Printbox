package ru.lionzxy.printbox.view.print_history.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.lionzxy.printbox.data.model.PrintHistory

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPrintHistoryView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onError(resId: Int)

    fun loadHistory(history: List<PrintHistory>)
    fun showLoad(visible: Boolean)
}
