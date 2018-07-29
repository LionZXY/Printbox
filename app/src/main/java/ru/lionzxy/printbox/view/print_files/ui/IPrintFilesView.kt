package ru.lionzxy.printbox.view.print_files.ui

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.lionzxy.printbox.data.model.PrintDocument

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPrintFilesView : MvpView {
    fun setFiles(docs: List<PrintDocument>)
    fun showLoading(visible: Boolean)
    @StateStrategyType(SkipStrategy::class)
    fun onError(@StringRes resId: Int)
}
