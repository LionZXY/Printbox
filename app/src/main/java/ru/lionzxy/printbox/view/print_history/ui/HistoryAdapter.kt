package ru.lionzxy.printbox.view.print_history.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.data.model.PrintHistoryStageEnum

class HistoryAdapter(var history: List<PrintHistory>,
                     var clickListener: (history: PrintHistory) -> Unit) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount() = history.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val element = history[position]
        if (element.status == PrintHistoryStageEnum.DONE.id) {
            holder.orderStatus.setBackgroundResource(R.drawable.circle_green)
        } else {
            holder.orderStatus.setBackgroundResource(R.drawable.circle_red)
        }
        holder.orderPrice.text = holder.itemView.context.resources.getString(R.string.history_item_price, (element.price.toDouble() / 100).toFloat())
        holder.orderFilename.text = element.document.name
        holder.orderFiledate.text = element.createdAt.toLocalDateTime().toString("yyyy-MM-dd HH:mm")
        holder.orderTextStatus.text = element.printerName
        holder.orderColor.text = element.colorOption.name
        holder.orderPages.text = holder.itemView.context.resources.getString(
                R.string.history_item_pages, element.document.pagesCount)
        holder.orderCopies.text = holder.itemView.context.resources.getString(
                R.string.history_item_copies, element.countTotal)
        holder.orderDuplex.text = element.duplexOption.name
        holder.cardView.setOnClickListener { clickListener.invoke(element) }

    }

    fun setList(history: List<PrintHistory>) {
        this.history = history
        notifyDataSetChanged()
    }

}

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

}