package ru.lionzxy.printbox.view.print.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintCartStage
import ru.lionzxy.printbox.di.print.PrintModule
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.view.print.ui.IPrintView
import javax.inject.Inject

@InjectViewState
class PrintPresenter : MvpPresenter<IPrintView>() {
    @Inject
    lateinit var interactor: IPrintInteractor
    private val disposable = CompositeDisposable()
    private lateinit var cartModel: PrintCartModel

    init {
        App.appComponent.plus(PrintModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposable.addAll(interactor.getObservableCart()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    cartModel = it
                    viewState.setCartModel(it)
                })
    }

    fun openState(state: PrintCartStage) {
        cartModel.stage = state
        interactor.setCart(cartModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}