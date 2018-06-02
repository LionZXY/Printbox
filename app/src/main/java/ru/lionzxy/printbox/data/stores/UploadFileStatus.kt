package ru.lionzxy.printbox.data.stores

import net.gotev.uploadservice.ServerResponse
import net.gotev.uploadservice.UploadInfo

data class UploadFileStatus(val uploadInfo: UploadInfo? = null,
                            val serverResponse: ServerResponse? = null)