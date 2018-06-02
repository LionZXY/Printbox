package ru.lionzxy.printbox.data.stores

import io.reactivex.Observable
import net.gotev.uploadservice.UploadInfo
import net.gotev.uploadservice.UploadStatusDelegate

interface IRxServiceUploadMonitoring : UploadStatusDelegate {
    fun getObservable(): Observable<UploadFileStatus>
}