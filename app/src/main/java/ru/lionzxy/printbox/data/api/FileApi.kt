package ru.lionzxy.printbox.data.api

import android.printservice.PrintDocument
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import ru.lionzxy.printbox.data.model.DocumentWrapper

interface FileApi {
    @GET("user_documents/")
    fun getUserFiles(): Observable<DocumentWrapper>

    @DELETE("user_documents/{id}/")
    fun removeDocument(@Path("id") id: Int): Completable
}