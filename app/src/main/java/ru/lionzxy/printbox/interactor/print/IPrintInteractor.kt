package ru.lionzxy.printbox.interactor.print

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.data.model.PrintPlace

interface IPrintInteractor {
    fun getPrinters(): Observable<List<PrintPlace>>
    fun getLastPrinter(): Single<PrintPlace>
    fun savePrinter(printPlace: PrintPlace): Completable

    fun getObservableCart(): Observable<PrintCartModel>
    fun setCart(printCartModel: PrintCartModel)
    fun getPrice(printCartModel: PrintCartModel): Single<Int>
    fun print(printCartModel: PrintCartModel): Single<PrintHistory>
}