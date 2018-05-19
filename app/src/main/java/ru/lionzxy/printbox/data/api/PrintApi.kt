package ru.lionzxy.printbox.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import ru.lionzxy.printbox.data.model.PrintPlace

interface PrintApi {
    @GET("printers/")
    fun getPrinters(): Observable<List<PrintPlace>>
}