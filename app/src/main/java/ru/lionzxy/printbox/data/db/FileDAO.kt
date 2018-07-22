package ru.lionzxy.printbox.data.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintDocument

@Dao
abstract class FileDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(document: PrintDocument)

    @Query("SELECT * FROM ${PrintDocument.TABLE_NAME} WHERE ${PrintDocument.TABLE_UPLOAD_ID} = :uploadId")
    abstract fun getByUploadId(uploadId: String): Single<PrintDocument>

    @Query("SELECT * FROM ${PrintDocument.TABLE_NAME} WHERE ${PrintDocument.TABLE_UPLOAD_ID} != ''")
    abstract fun getAllActiveDraft(): Flowable<List<PrintDocument>>

    @Delete
    abstract fun removeUploadFile(document: PrintDocument)
}