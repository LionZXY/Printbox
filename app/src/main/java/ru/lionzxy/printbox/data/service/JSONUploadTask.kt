package ru.lionzxy.printbox.data.service

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import net.gotev.uploadservice.NameValue
import net.gotev.uploadservice.UploadService
import net.gotev.uploadservice.UploadTask
import net.gotev.uploadservice.http.BodyWriter
import net.gotev.uploadservice.http.HttpConnection
import ru.lionzxy.printbox.utils.codec.Base64InputStream
import timber.log.Timber


class JSONUploadTask : UploadTask(), HttpConnection.RequestBodyDelegate, BodyWriter.OnStreamWriteListener {
    var parameter = JSONUploadTaskParameter()
    lateinit var contentResolver: ContentResolver
    lateinit var context: Context
    var connection: HttpConnection? = null

    override fun init(service: UploadService, intent: Intent) {
        super.init(service, intent)
        parameter = intent.getParcelableExtra(EXTRA_UPLOAD_TASK)
        contentResolver = service.applicationContext.contentResolver
        context = service.applicationContext
    }

    override fun upload() {

        try {
            successfullyUploadedFiles.clear()
            uploadedBytes = 0
            totalBytes = getTotalSize()

            connection = UploadService.HTTP_STACK
                    .createNewConnection("POST", params.serverUrl)
                    .setTotalBodyBytes(totalBytes, true)
                    .setHeaders(listOf(NameValue("Content-Type", "application/json")))

            val response = connection?.getResponse(this)
            Timber.i("Server responded with HTTP ${response?.httpCode} to upload with ID: ${params.id}")

            // Broadcast completion only if the user has not cancelled the operation.
            // It may happen that when the body is not completely written and the client
            // closes the connection, no exception is thrown here, and the server responds
            // with an HTTP status code. Without this, what happened was that completion was
            // broadcasted and then the cancellation. That behaviour was not desirable as the
            // library user couldn't execute code on user cancellation.
            if (shouldContinue) {
                broadcastCompleted(response)
            }

        } finally {
            connection?.close()
        }
    }

    override fun onBodyReady(bodyWriter: BodyWriter) {
        uploadedBytes = 0
        writeHeader(bodyWriter)
        writeFile(bodyWriter)
        writeFooter(bodyWriter)
        Timber.i("onBodyReady with task id: ${params.id} file ${getFileName()} complete")
    }

    private fun writeHeader(bodyWriter: BodyWriter) {
        val array = buildHeader().toByteArray()
        bodyWriter.write(array)
        bodyWriter.flush()
        uploadedBytes += array.size
        Timber.i("ProgressUpload for id ${params.id}: writeHeader $uploadedBytes/$totalBytes")
    }

    private fun buildHeader(): String {
        val fileName = getFileName()
        val mimeType = getMimeType(fileName)
        val sb = StringBuilder()
        sb.append("{\"name\":\"")
                .append(fileName)
                .append("\",\"content\":\"data:")
                .append(mimeType)
                .append(";base64,")
        return sb.toString()
    }

    private fun writeFile(bodyWriter: BodyWriter) {
        val fileStream = contentResolver.openInputStream(parameter.uri)
        val baseStream = Base64InputStream(fileStream, true, false)
        baseStream.use {
            bodyWriter.writeStream(baseStream, this@JSONUploadTask)
        }
        bodyWriter.flush()
        Timber.i("ProgressUpload for id ${params.id}: writeFile $uploadedBytes/$totalBytes")
    }

    private fun writeFooter(bodyWriter: BodyWriter) {
        val array = buildFooter().toByteArray()
        bodyWriter.write(array)
        bodyWriter.flush()
        uploadedBytes += array.size
        Timber.i("ProgressUpload for id ${params.id}: writeFooter $uploadedBytes/$totalBytes")
    }

    private fun buildFooter(): String {
        return "\"}"
    }

    private fun getMimeType(fileName: String): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(fileName) ?: ".doc"
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "application/octet-stream"
    }

    private fun getFileSize(): Long {
        var size = 0L
        Base64InputStream(contentResolver.openInputStream(parameter.uri), true, true).use {
            val buf = ByteArray(DEFAULT_BUFFER_SIZE)

            var result = 0
            while (result != -1) {
                size += result
                result = it.read(buf, 0, buf.size)
            }
        }
        return size
    }

    private fun getTotalSize(): Long {
        val fileSize = getFileSize()
        val headerSize = buildHeader().toByteArray().size
        val footerSize = buildFooter().toByteArray().size
        Timber.i("Upload file size for ${params.id}: fileSize($fileSize) + headerSize($headerSize) + footerSize($footerSize) = ${fileSize + headerSize + footerSize}")
        return fileSize + headerSize + footerSize
    }

    private fun getFileName(): String {
        val uri = parameter.uri
        var result: String? = null
        if (uri.scheme == "content") {
            contentResolver.query(uri, null, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }
        return result as String
    }

    override fun shouldContinueWriting(): Boolean {
        return shouldContinue
    }

    override fun onBytesWritten(bytesWritten: Int) {
        uploadedBytes += bytesWritten.toLong()
        broadcastProgress(uploadedBytes, totalBytes)
    }
}