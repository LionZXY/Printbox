package ru.lionzxy.printbox.view.print_select.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintOption

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPrintSelectView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun openPrintMapSelect()
    fun onUpdateCartModel(cartModel: PrintCartModel)

    fun openDialog(visible: Boolean, items: List<PrintOption> = emptyList())
}
