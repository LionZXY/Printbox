package ru.lionzxy.printbox.interactor.print

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintPlace
import ru.lionzxy.printbox.repository.print.IPrintRepository

class PrintInteractor(
        val printRepository: IPrintRepository
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
}