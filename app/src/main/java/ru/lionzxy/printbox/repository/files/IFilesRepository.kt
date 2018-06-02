package ru.lionzxy.printbox.repository.files

import android.net.Uri
import android.printservice.PrintDocument
import io.reactivex.Completable
import io.reactivex.Observable
import net.gotev.uploadservice.UploadInfo
import ru.lionzxy.printbox.data.stores.UploadFileStatus
import java.io.File

interface IFilesRepository {
    fun getUserFiles(): Observable<List<ru.lionzxy.printbox.data.model.PrintDocument>>
    fun removeUserFile(id: Int): Completable
    fun uploadFile(uri: Uri): Observable<UploadFileStatus>
}