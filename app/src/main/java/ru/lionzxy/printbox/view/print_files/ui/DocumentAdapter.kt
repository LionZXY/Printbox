package ru.lionzxy.printbox.view.print_files.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintDocument

class DocumentAdapter(var files: List<PrintDocument>) : RecyclerView.Adapter<DocumentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return DocumentViewHolder(view)
    }

    override fun getItemCount() = files.size

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val doc = files[position]

        holder.nameText.text = doc.name
        holder.createdAt.text = doc.createdAt.toLocalDateTime().toString("yyyy-MM-dd HH:mm")
    }

    fun setList(files: List<PrintDocument>) {
        this.files = files
        notifyDataSetChanged()
    }

}

class DocumentViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val nameText = rootView.findViewById<TextView>(R.id.file_name)
    val createdAt = rootView.findViewById<TextView>(R.id.file_createdat)
    val contentCard = rootView.findViewById<CardView>(R.id.content_card)
}