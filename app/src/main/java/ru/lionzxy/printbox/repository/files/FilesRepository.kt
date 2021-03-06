package ru.lionzxy.printbox.repository.files

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import net.gotev.uploadservice.UploadNotificationConfig
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.FileApi
import ru.lionzxy.printbox.data.db.FileDAO
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.data.service.JSONUploadRequest
import ru.lionzxy.printbox.data.stores.RxServiceUploadMonitoring
import ru.lionzxy.printbox.data.stores.UploadFileStatus
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.utils.getFileName

class FilesRepository(retrofit: Retrofit, private val context: Context,
                      private val dao: FileDAO,
                      private val contentResolver: ContentResolver) : IFilesRepository {
    private val fileApi = retrofit.create(FileApi::class.java)

    override fun getUserFiles(): Observable<List<PrintDocument>> {
        return fileApi.getUserFiles()
                .map { it.results }
                .subscribeOn(Schedulers.io())
    }

    override fun getFileDraftUpload(): Flowable<List<PrintDocument>> {
        return dao.getAllActiveDraft()
                .subscribeOn(Schedulers.io())
    }

    override fun removeUserFile(id: Int): Completable {
        return fileApi.removeDocument(id)
                .subscribeOn(Schedulers.io())
    }

    override fun removeUploadTask(uploadId: String): Completable {
        return Completable.fromCallable { dao.removeFileById(uploadId) }
                .subscribeOn(Schedulers.io())

    }

    override fun getFileById(id: Int): Single<PrintDocument> {
        return fileApi.getFileById(id)
                .subscribeOn(Schedulers.io())
    }

    override fun uploadFile(uri: Uri): Completable {
        val uploadId = JSONUploadRequest(context, Constants.UPLOAD_FILE_URL)
                .setUri(uri)
                .setNotificationConfig(UploadNotificationConfig())
                .startUpload()
        val printDocument = PrintDocument.getUploadObject(uploadId)
        printDocument.name = uri.getFileName(contentResolver)
        return Completable.fromCallable { dao.insertOrUpdate(printDocument) }
                .subscribeOn(Schedulers.io())
    }
}