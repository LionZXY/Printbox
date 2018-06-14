package ru.lionzxy.printbox.view.print_select.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.lionzxy.printbox.data.model.PrintCartModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPrintSelectView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun openPrintMapSelect()
    fun onUpdateCartModel(cartModel: PrintCartModel)
}
