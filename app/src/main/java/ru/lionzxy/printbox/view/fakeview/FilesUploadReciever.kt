package ru.lionzxy.printbox.view.fakeview

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import net.gotev.uploadservice.ServerResponse
import net.gotev.uploadservice.UploadInfo
import net.gotev.uploadservice.UploadServiceBroadcastReceiver
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.di.files.FilesModule
import ru.lionzxy.printbox.interactor.files.IFilesInteractor
import ru.lionzxy.printbox.utils.toast
import timber.log.Timber
import javax.inject.Inject


class FilesUploadReciever : UploadServiceBroadcastReceiver() {
    @Inject
    lateinit var interactor: IFilesInteractor
    private val disposable = CompositeDisposable()

    init {
        App.appComponent.plus(FilesModule()).inject(this)
    }

    override fun onProgress(context: Context, uploadInfo: UploadInfo) {
        //TODO: Notify progress in DB
        Timber.i("Upload ${uploadInfo.uploadId}: ${uploadInfo.uploadedBytes}/${uploadInfo.totalBytes}")
    }

    override fun onError(context: Context, uploadInfo: UploadInfo, serverResponse: ServerResponse, exception: Exception) {
        context.toast(R.string.files_upload_failed)
        Timber.e("Error upload file. ${uploadInfo.uploadId}. HTTP ${serverResponse.httpCode}: ${serverResponse.bodyAsString}")
        disposable.addAll(interactor.removeUploadTask(uploadInfo.uploadId)
                .subscribe({
                    Timber.d("Remove ${uploadInfo.uploadId} from DB done!")
                }, Timber::e))
    }

    override fun onCompleted(context: Context, uploadInfo: UploadInfo, serverResponse: ServerResponse) {
        context.toast(R.string.files_upload_finished)
        Timber.i("File upload sucsf: ${uploadInfo.uploadId}. HTTP ${serverResponse.httpCode}: ${serverResponse.bodyAsString}")
        disposable.addAll(interactor.removeUploadTask(uploadInfo.uploadId)
                .subscribe({
                    Timber.d("Remove ${uploadInfo.uploadId} from DB done!")
                }, Timber::e))
    }

    override fun onCancelled(context: Context, uploadInfo: UploadInfo) {
        context.toast(R.string.files_upload_canceled)
        Timber.i("Task ${uploadInfo.uploadId} is canceled")
    }

    override fun unregister(context: Context?) {
        super.unregister(context)
        disposable.clear()
    }
}