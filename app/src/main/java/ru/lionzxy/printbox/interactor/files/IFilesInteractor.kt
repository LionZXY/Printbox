package ru.lionzxy.printbox.interactor.files

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.data.stores.UploadFileStatus

interface IFilesInteractor {
    fun getUserFiles(): Flowable<List<PrintDocument>>
    fun removeUserFile(id: Int): Completable
    fun uploadFile(uri: Uri): Completable
    fun removeUploadTask(uploadId: String): Completable
    fun onFileSelect(document: PrintDocument): Completable
    fun getFileById(id: Int): Single<PrintDocument>
}