package ru.lionzxy.printbox.repository.print

import io.reactivex.Observable
import ru.lionzxy.printbox.data.model.PrintPlace

interface IPrintRepository {
    fun getPrinters(): Observable<List<PrintPlace>>
}