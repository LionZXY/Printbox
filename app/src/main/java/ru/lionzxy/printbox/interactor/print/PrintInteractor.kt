package ru.lionzxy.printbox.interactor.print

import io.reactivex.Observable
import ru.lionzxy.printbox.data.model.PrintPlace
import ru.lionzxy.printbox.repository.print.IPrintRepository

class PrintInteractor(
        val printRepository: IPrintRepository
) : IPrintInteractor {
    override fun getPrinters(): Observable<List<PrintPlace>> {
        return printRepository.getPrinters()
    }
}