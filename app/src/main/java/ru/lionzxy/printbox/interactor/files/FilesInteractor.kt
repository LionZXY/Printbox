package ru.lionzxy.printbox.interactor.files

import android.net.Uri
import io.reactivex.*
import io.reactivex.functions.BiFunction
import ru.lionzxy.printbox.data.model.PrintCartStage
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.data.stores.UploadFileStatus
import ru.lionzxy.printbox.repository.files.IFilesRepository
import ru.lionzxy.printbox.repository.print.IPrintRepository

class FilesInteractor(
        val filesRepository: IFilesRepository,
        val printRepository: IPrintRepository
) : IFilesInteractor {
    override fun getUserFiles(): Flowable<List<PrintDocument>> {
        return filesRepository.getFileDraftUpload()
                .flatMap { drafts ->
                    filesRepository.getUserFiles()
                            .map { drafts.plus(it) }
                            .toFlowable(BackpressureStrategy.BUFFER)
                }
    }

    override fun removeUserFile(id: Int): Completable {
        return filesRepository.removeUserFile(id)
    }

    override fun uploadFile(uri: Uri): Completable {
        return filesRepository.uploadFile(uri)
    }

    override fun onFileSelect(document: PrintDocument): Completable {
        return printRepository.getObservableCart()
                .firstElement()
                .flatMapCompletable { print ->
                    Completable.fromCallable {
                        print.printDocument = document
                        print.stage = PrintCartStage.OPTIONS
                        printRepository.setCart(print)
                    }
                }
    }

    override fun removeUploadTask(uploadId: String): Completable {
        return filesRepository.removeUploadTask(uploadId)
    }

    override fun getFileById(id: Int): Single<PrintDocument> {
        return filesRepository.getFileById(id)
    }
}