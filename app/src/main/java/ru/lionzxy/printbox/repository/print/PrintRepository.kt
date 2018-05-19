package ru.lionzxy.printbox.repository.print

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.PrintApi
import ru.lionzxy.printbox.data.model.PrintPlace

class PrintRepository(retrofit: Retrofit) : IPrintRepository {
    private val printApi = retrofit.create(PrintApi::class.java)

    override fun getPrinters(): Observable<List<PrintPlace>> {
        return printApi.getPrinters().subscribeOn(Schedulers.io())
    }

}