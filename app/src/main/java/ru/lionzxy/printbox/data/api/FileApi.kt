package ru.lionzxy.printbox.data.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import ru.lionzxy.printbox.data.model.DocumentWrapper
import ru.lionzxy.printbox.data.model.PrintDocument

interface FileApi {
    @GET("user_documents/")
    fun getUserFiles(): Observable<DocumentWrapper>

    @GET("user_documents/{id}/")
    fun getFileById(@Path("id") id: Int): Single<PrintDocument>

    @DELETE("user_documents/{id}/")
    fun removeDocument(@Path("id") id: Int): Completable
}