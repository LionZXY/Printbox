package ru.lionzxy.printbox.data.stores

import android.content.Context
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.gotev.uploadservice.ServerResponse
import net.gotev.uploadservice.UploadInfo
import java.lang.Exception

class RxServiceUploadMonitoring() : IRxServiceUploadMonitoring {
    private val subject = BehaviorSubject.create<UploadFileStatus>()

    override fun getObservable(): Observable<UploadFileStatus> {
        return subject
    }

    override fun onCancelled(context: Context?, uploadInfo: UploadInfo?) {
        if (uploadInfo != null) {
            subject.onNext(UploadFileStatus(uploadInfo))
        }
        subject.onComplete()
    }

    override fun onProgress(context: Context?, uploadInfo: UploadInfo?) {
        uploadInfo?.let { subject.onNext(UploadFileStatus(uploadInfo)) }
    }

    override fun onError(context: Context?, uploadInfo: UploadInfo?, serverResponse: ServerResponse?, exception: Exception?) {
        if (uploadInfo != null) {
            subject.onNext(UploadFileStatus(uploadInfo, serverResponse))
        }
        exception?.let { subject.onError(it) }
    }

    override fun onCompleted(context: Context?, uploadInfo: UploadInfo?, serverResponse: ServerResponse?) {
        if (uploadInfo != null) {
            subject.onNext(UploadFileStatus(uploadInfo, serverResponse))
        }

        subject.onComplete()
    }
}