package ru.lionzxy.printbox.interactor.files

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.data.stores.UploadFileStatus
import ru.lionzxy.printbox.repository.files.IFilesRepository

class FilesInteractor(
        val filesRepository: IFilesRepository
) : IFilesInteractor {
    override fun getUserFiles(): Observable<List<PrintDocument>> {
        return filesRepository.getUserFiles()
    }

    override fun removeUserFile(id: Int): Completable {
        return filesRepository.removeUserFile(id)
    }

    override fun uploadFile(uri: Uri): Observable<UploadFileStatus> {
        return filesRepository.uploadFile(uri)
    }
}