package ru.lionzxy.printbox.data.db

import android.arch.persistence.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintDocument

@Dao
abstract class FileDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(document: PrintDocument)

    @Query("SELECT * FROM ${PrintDocument.TABLE_NAME} WHERE ${PrintDocument.TABLE_UPLOAD_ID} = :uploadId")
    abstract fun getByUploadId(uploadId: String): Single<PrintDocument>

    @Query("SELECT * FROM ${PrintDocument.TABLE_NAME} WHERE ${PrintDocument.TABLE_UPLOAD_ID} != '' ORDER BY createdAt DESC")
    abstract fun getAllActiveDraft(): Flowable<List<PrintDocument>>

    @Query("DELETE FROM ${PrintDocument.TABLE_NAME} WHERE ${PrintDocument.TABLE_UPLOAD_ID} == :uploadId")
    abstract fun removeFileById(uploadId: String)

    @Delete
    abstract fun removeUploadFile(document: PrintDocument)
}