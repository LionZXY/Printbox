package ru.lionzxy.printbox.interactor.files

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.data.stores.UploadFileStatus

interface IFilesInteractor {
    fun getUserFiles(): Observable<List<ru.lionzxy.printbox.data.model.PrintDocument>>
    fun removeUserFile(id: Int): Completable
    fun uploadFile(uri: Uri): Observable<UploadFileStatus>
    fun onFileSelect(document: PrintDocument): Completable
}