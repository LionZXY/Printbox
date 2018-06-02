package ru.lionzxy.printbox.data.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import net.gotev.uploadservice.UploadRequest
import net.gotev.uploadservice.UploadTask

class JSONUploadRequest(context: Context, serverUrl: String) :
        UploadRequest<JSONUploadRequest>(context, null, serverUrl) {
    val jsonParams = JSONUploadTaskParameter(serverUrl)

    override fun getTaskClass(): Class<out UploadTask> {
        return JSONUploadTask::class.java
    }

    fun setUri(uri: Uri): JSONUploadRequest {
        jsonParams.uri = uri
        return this
    }

    override fun initializeIntent(intent: Intent) {
        super.initializeIntent(intent)
        intent.putExtra(EXTRA_UPLOAD_TASK, jsonParams)
    }
}