package ru.lionzxy.printbox.view.print_select.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintCartStage
import ru.lionzxy.printbox.data.model.PrintFinalOrder
import ru.lionzxy.printbox.data.model.PrintOption
import ru.lionzxy.printbox.di.print.PrintModule
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.view.print_select.ui.IPrintSelectView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class PrintSelectPresenter : MvpPresenter<IPrintSelectView>() {
    private val disposable = CompositeDisposable()
    @Inject
    lateinit var interactor: IPrintInteractor
    private lateinit var printCartModel: PrintCartModel
    private var priceSubscriber: Disposable? = null

    init {
        App.appComponent.plus(PrintModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposable.addAll(interactor.getObservableCart()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    printCartModel = it
                    viewState.onUpdateCartModel(it)
                    if (printCartModel.printPlace == null) {
                        viewState.openPrintMapSelect()
                    }

                    var changed = false
                    if (printCartModel.printOrder == null) {
                        printCartModel.printOrder = PrintFinalOrder()
                        changed = true
                    }

                    if (printCartModel.printOrder?.duplexOption == null) {
                        printCartModel.printOrder?.duplexOption = printCartModel.printPlace?.optionDoublePage?.firstOrNull() ?: PrintOption()
                        changed = true
                    }

                    if (printCartModel.printOrder?.colorOption == null) {
                        printCartModel.printOrder?.colorOption = printCartModel.printPlace?.optionColor?.firstOrNull() ?: PrintOption()
                        changed = true
                    }
                    if (changed) {
                        interactor.setCart(printCartModel)
                    }
                    loadPrice()
                })
    }

    fun loadPrice() {
        viewState.priceLoadingShow()
        priceSubscriber?.dispose()
        val tmp = interactor.getPrice(printCartModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.setPrice((it / 100).toFloat(), printCartModel.printOrder!!.copies) }, Timber::e)
        priceSubscriber = tmp
        disposable.addAll(tmp)
    }

    fun onSelectPrintOption(option: PrintOption) {
        printCartModel.printOrder?.duplexOption = option
        interactor.setCart(printCartModel)
        viewState.openDialog(false)
    }

    fun onSelectCopiesCount(count: Int) {
        printCartModel.printOrder?.copies = count
        interactor.setCart(printCartModel)
    }

    fun onClickSelectOption() {
        printCartModel.printPlace?.optionDoublePage?.let {
            viewState.openDialog(true, it)
        }
    }

    fun onBack() {
        printCartModel.stage = PrintCartStage.SELECTION_FILE
        interactor.setCart(printCartModel)
    }

    fun onPrintClick() {
        viewState.printProgress(true)
        disposable.addAll(interactor.print(printCartModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    printCartModel.stage = PrintCartStage.HISTORY
                    interactor.setCart(printCartModel)
                    viewState.printProgress(false)
                }, {
                    Timber.e(it)
                    viewState.onError(R.string.option_print_error)
                    viewState.printProgress(false)
                }))
    }

    fun openFileChange() {
        val model = printCartModel
        model.stage = PrintCartStage.SELECTION_FILE
        interactor.setCart(model)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}