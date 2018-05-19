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
import timber.log.Timber
import javax.inject.Inject


@InjectViewState
class PrintMapPresenter : MvpPresenter<IPrintMapView>() {
    private val disposable = CompositeDisposable()
    @Inject
    lateinit var interactor: IPrintInteractor
    private var printPlaceAlreadySelect = false

    init {
        App.appComponent.plus(PrintModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showPrinterLoading(true)
        viewState.setActiveNext(false)
        disposable.addAll(interactor
                .getPrinters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setPrinters(it)
                    viewState.showPrinterLoading(false)
                }, {
                    Timber.e(it)
                    viewState.onError(R.string.map_printer_error)
                    viewState.showPrinterLoading(false)
                }))
        disposable.addAll(interactor
                .getLastPrinter()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!printPlaceAlreadySelect) {
                        if (it.isEmpty()) {
                            viewState.requestCurPosition()
                        } else {
                            viewState.selectPrintPlace(it)
                        }
                    }
                    printPlaceAlreadySelect = true
                }, Timber::e))
    }

    fun onNext(printer: PrintPlace?) {
        if (printer == null) {
            Timber.e("Next value must not be null!")
        }

        viewState.showProgressBarNext(true)
        disposable.addAll(interactor.savePrinter(printer!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.finishWithPrinter(printer)
                    viewState.showProgressBarNext(false)
                }, {
                    Timber.e(it)
                    viewState.onError(R.string.map_draft_error)
                    viewState.showProgressBarNext(false)
                }))
    }

    fun onSelectPrinter(printer: PrintPlace) {
        viewState.selectPrintPlace(printer)
        printPlaceAlreadySelect = true
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