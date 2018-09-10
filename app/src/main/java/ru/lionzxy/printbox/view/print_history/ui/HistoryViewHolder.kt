package ru.lionzxy.printbox.view.print_history.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.lionzxy.printbox.App
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.data.model.PrintHistoryStageEnum
import ru.lionzxy.printbox.di.history.HistoryModule
import ru.lionzxy.printbox.interactor.history.IHistoryInteractor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HistoryViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val orderStatus = rootView.findViewById<View>(R.id.order_status)
    val orderPrice = rootView.findViewById<TextView>(R.id.order_price)
    val orderFilename = rootView.findViewById<TextView>(R.id.order_filename)
    val orderFiledate = rootView.findViewById<TextView>(R.id.order_filedate)
    val orderTextStatus = rootView.findViewById<TextView>(R.id.order_text_status)
    val orderColor = rootView.findViewById<TextView>(R.id.order_color)
    val orderPages = rootView.findViewById<TextView>(R.id.order_pages)
    val orderCopies = rootView.findViewById<TextView>(R.id.order_copy)
    val orderDuplex = rootView.findViewById<TextView>(R.id.order_type)
    val cardView = rootView.findViewById<CardView>(R.id.cardview)

    private var disposable: Disposable? = null
    @Inject
    lateinit var interactor: IHistoryInteractor

    init {
        App.appComponent.plus(HistoryModule()).inject(this)
    }

    fun onNewHistory(printHistory: PrintHistory) {
        setStatus(printHistory)
        setNewHistory(printHistory)
        disposable?.dispose()
        if (printHistory.status != PrintHistoryStageEnum.DONE.id
                || printHistory.status != PrintHistoryStageEnum.FAILED.id) {
            disposable = Observable.interval(2, TimeUnit.SECONDS)
                    .flatMap { interactor.getHistoryById(printHistory.id).toObservable() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .takeUntil {
                        it.status == PrintHistoryStageEnum.DONE.id || it.status == PrintHistoryStageEnum.FAILED.id
                    }
                    .subscribe({
                        setStatus(it)
                        setNewHistory(it)
                    }, Timber::e)
        }
    }

    private fun setNewHistory(newDoc: PrintHistory) {
        orderPrice.text = itemView.context.resources.getString(R.string.history_item_price, (newDoc.price.toDouble() / 100).toFloat())
        orderFilename.text = newDoc.document.name
        orderFiledate.text = newDoc.createdAt.toLocalDateTime().toString("yyyy-MM-dd HH:mm")
        orderTextStatus.text = newDoc.printerName
        orderColor.text = newDoc.colorOption.name
        orderPages.text = itemView.context.resources.getString(
                R.string.history_item_pages, newDoc.document.pagesCount)
        orderCopies.text = itemView.context.resources.getString(
                R.string.history_item_copies, newDoc.countTotal)
        orderDuplex.text = newDoc.duplexOption.name
    }

    private fun setStatus(doc: PrintHistory) {
        when {
            doc.status == PrintHistoryStageEnum.DONE.id -> orderStatus.setBackgroundResource(R.drawable.circle_green)
            doc.status == PrintHistoryStageEnum.FAILED.id -> orderStatus.setBackgroundResource(R.drawable.circle_red)
            else -> orderStatus.setBackgroundResource(R.drawable.circle_yellow)
        }
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}