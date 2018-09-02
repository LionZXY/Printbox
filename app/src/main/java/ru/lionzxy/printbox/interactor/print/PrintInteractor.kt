package ru.lionzxy.printbox.interactor.print

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.data.model.PrintPlace
import ru.lionzxy.printbox.repository.pay.IPayRepository
import ru.lionzxy.printbox.repository.print.IPrintRepository

class PrintInteractor(
        val printRepository: IPrintRepository,
        val payRepository: IPayRepository
) : IPrintInteractor {
    override fun getPrinters(): Observable<List<PrintPlace>> {
        return printRepository.getPrinters()
    }

    override fun getLastPrinter(): Single<PrintPlace> {
        return printRepository.getLastPrinter()
    }

    override fun savePrinter(printPlace: PrintPlace): Completable {
        return printRepository.savePrinter(printPlace)
    }

    override fun getObservableCart(): Observable<PrintCartModel> {
        return printRepository.getObservableCart()
    }

    override fun setCart(printCartModel: PrintCartModel) {
        printRepository.setCart(printCartModel)
    }

    override fun getPrice(printCartModel: PrintCartModel): Single<Int> {
        return printRepository.getPrice(printCartModel)
    }

    override fun print(printCartModel: PrintCartModel): Single<PrintHistory> {
        return printRepository.print(printCartModel)
    }

    override fun getBalanceObservable(): Observable<Double> {
        return payRepository.getBalanceObservable()
    }
}