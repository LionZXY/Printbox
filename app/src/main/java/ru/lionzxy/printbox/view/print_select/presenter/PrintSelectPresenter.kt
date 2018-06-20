package ru.lionzxy.printbox.view.print_select.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintCartStage
import ru.lionzxy.printbox.data.model.PrintOption
import ru.lionzxy.printbox.di.print.PrintModule
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.view.print_select.ui.IPrintSelectView
import javax.inject.Inject

@InjectViewState
class PrintSelectPresenter : MvpPresenter<IPrintSelectView>() {
    private val disposable = CompositeDisposable()
    @Inject
    lateinit var interactor: IPrintInteractor
    private lateinit var printCartModel: PrintCartModel

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

                    if(printCartModel.printPlace != null && printCartModel.printOption == null) {
                        printCartModel.printOption = printCartModel.printPlace?.optionDoublePage?.firstOrNull() ?: PrintOption()
                        interactor.setCart(printCartModel)
                    }
                })
    }

    fun onSelectPrintOption(option: PrintOption) {
        printCartModel.printOption = option
        interactor.setCart(printCartModel)
        viewState.openDialog(false)
    }

    fun onClickSelectOption() {
        printCartModel.printPlace?.optionDoublePage?.let {
            viewState.openDialog(true, it)
        }
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