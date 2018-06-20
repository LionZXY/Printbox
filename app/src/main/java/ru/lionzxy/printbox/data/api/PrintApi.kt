package ru.lionzxy.printbox.data.api

import io.reactivex.Observable
import retrofit2.http.*
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.data.model.PrintPlace

interface PrintApi {
    @GET("printers/")
    fun getPrinters(): Observable<List<PrintPlace>>

    @POST("print_operations/")
    fun finalPrint(@Body printOrderApi: PrintOrderApi): Observable<PrintHistory>

    @GET("user_documents/{id}/get_cost/")
    fun getPrice(@Path("id") docId: Int,
                 @Query("document") retryDocId: Int,
                 @Query("printer") printer: Int,
                 @Query("copies") copies: Int,
                 @Query("color") color: Int,
                 @Query("duplex") duplex: Int): Observable<List<Int>>
}