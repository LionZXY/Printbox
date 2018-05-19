package ru.lionzxy.printbox.view.print_map.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.yandex.mapkit.map.CameraPosition
import ru.lionzxy.printbox.data.model.PrintPlace

@StateStrategyType(AddToEndSingleStrategy::class)
interface IPrintMapView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun requestCurPosition()

    fun setPosition(position: CameraPosition)

    @StateStrategyType(SkipStrategy::class)
    fun setCurrentPosition(position: CameraPosition)
    @StateStrategyType(SkipStrategy::class)
    fun finishWithPrinter(printPlace: PrintPlace)

    fun setPrinters(list: List<PrintPlace>)
    fun onError(resId: Int)
    fun showPrinterLoading(visible: Boolean)
    fun invalidate()
    fun selectPrintPlace(printPlace: PrintPlace)
    fun setActiveNext(visible: Boolean)
    fun showProgressBarNext(visible: Boolean)
}
