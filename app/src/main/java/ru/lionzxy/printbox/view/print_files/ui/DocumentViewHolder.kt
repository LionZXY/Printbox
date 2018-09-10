package ru.lionzxy.printbox.view.print_files.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.DocumentStageEnum
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.di.files.FilesModule
import ru.lionzxy.printbox.interactor.files.IFilesInteractor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DocumentViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val nameText = rootView.findViewById<TextView>(R.id.file_name)
    val createdAt = rootView.findViewById<TextView>(R.id.file_createdat)
    val contentCard = rootView.findViewById<CardView>(R.id.content_card)
    val icon = rootView.findViewById<ImageView>(R.id.icon_file)

    private var disposable: Disposable? = null
    @Inject
    lateinit var interactor: IFilesInteractor

    init {
        App.appComponent.plus(FilesModule()).inject(this)
    }

    fun onNewDocument(printDocument: PrintDocument) {
        setStatus(printDocument)
        disposable?.dispose()
        if (printDocument.status != DocumentStageEnum.READY.id) {
            disposable = Observable.interval(2, TimeUnit.SECONDS)
                    .filter { printDocument.status != DocumentStageEnum.UPLOADING.id }
                    .flatMap { interactor.getFileById(printDocument.id).toObservable() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .takeUntil {
                        Timber.i("Test1:" + it.status.toString())
                        it.status == DocumentStageEnum.READY.id
                    }
                    .subscribe({
                        Timber.i("Test2:" + it.status.toString())
                        setStatus(it)
                        setNewDoc(printDocument, it)
                    }, Timber::e)
        }
    }

    private fun setNewDoc(oldDoc: PrintDocument, newDoc: PrintDocument) {
        oldDoc.status = newDoc.status
        oldDoc.statusName = newDoc.statusName
        oldDoc.colorPercent = newDoc.colorPercent
        oldDoc.pagesCount = newDoc.pagesCount
        oldDoc.pdfUrl = newDoc.pdfUrl
    }

    private fun setStatus(doc: PrintDocument) {
        if (doc.status == DocumentStageEnum.READY.id) {
            icon.setImageResource(R.drawable.ic_print_file)
        } else if (doc.status == DocumentStageEnum.PROCESSING.id) {
            icon.setImageResource(R.drawable.ic_processing)
        } else if (doc.status == DocumentStageEnum.UPLOADING.id) {
            icon.setImageResource(R.drawable.ic_action_upload)
        }
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}