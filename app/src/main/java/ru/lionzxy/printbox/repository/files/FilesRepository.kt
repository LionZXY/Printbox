package ru.lionzxy.printbox.repository.files

import android.content.Context
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.FileApi
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.data.service.JSONUploadRequest
import ru.lionzxy.printbox.data.stores.RxServiceUploadMonitoring
import ru.lionzxy.printbox.data.stores.UploadFileStatus
import ru.lionzxy.printbox.utils.Constants

class FilesRepository(retrofit: Retrofit, private val context: Context) : IFilesRepository {
    private val fileApi = retrofit.create(FileApi::class.java)

    override fun getUserFiles(): Observable<List<PrintDocument>> {
        return fileApi.getUserFiles()
                .map { it.results }
                .subscribeOn(Schedulers.io())
    }

    override fun removeUserFile(id: Int): Completable {
        return fileApi.removeDocument(id)
                .subscribeOn(Schedulers.io())
    }

    override fun uploadFile(uri: Uri): Observable<UploadFileStatus> {
        val observable = RxServiceUploadMonitoring()
        val uploadId = JSONUploadRequest(context, Constants.UPLOAD_FILE_URL)
                .setUri(uri)
                .setDelegate(observable)
                .startUpload()
        return observable.getObservable()
    }
}