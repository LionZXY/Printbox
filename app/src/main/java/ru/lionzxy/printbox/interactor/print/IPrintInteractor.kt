package ru.lionzxy.printbox.interactor.print

import io.reactivex.Observable
import ru.lionzxy.printbox.data.model.PrintPlace

interface IPrintInteractor {
    fun getPrinters(): Observable<List<PrintPlace>>
}