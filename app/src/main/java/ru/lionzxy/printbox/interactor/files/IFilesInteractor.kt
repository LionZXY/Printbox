package ru.lionzxy.printbox.interactor.files

import android.printservice.PrintDocument
import io.reactivex.Completable
import io.reactivex.Observable

interface IFilesInteractor {
    fun getUserFiles(): Observable<List<ru.lionzxy.printbox.data.model.PrintDocument>>
    fun removeUserFile(id: Int): Completable
}