package ru.lionzxy.printbox.repository.files

import android.printservice.PrintDocument
import io.reactivex.Completable
import io.reactivex.Observable

interface IFilesRepository {
    fun getUserFiles(): Observable<List<ru.lionzxy.printbox.data.model.PrintDocument>>
    fun removeUserFile(id: Int): Completable
}