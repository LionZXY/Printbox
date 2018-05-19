package ru.lionzxy.printbox.view.print_map.presenter

import android.location.Location
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintPlace
import ru.lionzxy.printbox.di.print.PrintModule
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.view.print_map.ui.IPrintMapView
import javax.inject.Inject


@InjectViewState
class PrintMapPresenter : MvpPresenter<IPrintMapView>() {
    private val disposable = CompositeDisposable()
    @Inject
    lateinit var interactor: IPrintInteractor

    init {
        App.appComponent.plus(PrintModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.requestCurPosition()
        viewState.showPrinterLoading(true)
        disposable.addAll(interactor
                .getPrinters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setPrinters(it)
                    viewState.showPrinterLoading(false)
                }, {
                    viewState.onError(R.string.map_printer_error)
                    viewState.showPrinterLoading(false)
                }))
    }

    fun onSelectPrinter(printer: PrintPlace) {
        viewState.selectPrintPlace(printer)
    }

    fun onLastLocation(location: Location) {
        val cameraPosition = CameraPosition(Point(location.latitude, location.longitude),
                15f, 0f, 0f)
        viewState.setCurrentPosition(cameraPosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}