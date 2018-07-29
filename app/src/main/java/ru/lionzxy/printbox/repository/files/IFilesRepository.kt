package ru.lionzxy.printbox.repository.files

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintDocument

interface IFilesRepository {
    fun getUserFiles(): Observable<List<PrintDocument>>
    fun removeUserFile(id: Int): Completable
    fun uploadFile(uri: Uri): Completable
    fun getFileDraftUpload(): Flowable<List<PrintDocument>>
    fun removeUploadTask(uploadId: String): Completable
    fun getFileById(id: Int): Single<PrintDocument>
}