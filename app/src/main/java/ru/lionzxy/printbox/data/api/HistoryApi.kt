package ru.lionzxy.printbox.data.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.lionzxy.printbox.data.model.HistoryWrapper
import ru.lionzxy.printbox.data.model.PrintHistory

interface HistoryApi {
    @GET("print_operations/")
    fun getHistory(): Observable<HistoryWrapper>

    @GET("print_operations/{id}/")
    fun getHistoryByid(@Path("id") id: Int): Single<PrintHistory>
}