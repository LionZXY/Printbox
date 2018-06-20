package ru.lionzxy.printbox.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import ru.lionzxy.printbox.data.model.HistoryWrapper
import ru.lionzxy.printbox.data.model.PrintHistory

interface HistoryApi {
    @GET("print_operations/")
    fun getHistory(): Observable<HistoryWrapper>
}