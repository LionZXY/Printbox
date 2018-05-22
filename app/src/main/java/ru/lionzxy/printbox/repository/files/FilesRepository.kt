package ru.lionzxy.printbox.repository.files

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.FileApi
import ru.lionzxy.printbox.data.model.PrintDocument

class FilesRepository(retrofit: Retrofit) : IFilesRepository {
    private val fileApi = retrofit.create(FileApi::class.java)

    override fun getUserFiles(): Observable<List<PrintDocument>> {
        return fileApi.getUserFiles()
                .map { it.results }
                .subscribeOn(Schedulers.io())
    }

    override fun removeUserFile(id: Int): Completable {
        return fileApi.removeDocument(id)
                .subscribeOn(Schedulers.io())
    }
}