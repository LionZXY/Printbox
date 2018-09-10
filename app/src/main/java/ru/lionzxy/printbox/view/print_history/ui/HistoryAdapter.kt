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
        holder.onNewHistory(element)
        holder.cardView.setOnClickListener { clickListener.invoke(element) }
    }

    fun setList(history: List<PrintHistory>) {
        this.history = history
        notifyDataSetChanged()
    }

}