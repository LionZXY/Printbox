package ru.lionzxy.printbox.view.print_files.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.DocumentStageEnum
import ru.lionzxy.printbox.data.model.PrintDocument

class DocumentAdapter(var files: List<PrintDocument>,
                      var clickListener: (doc: PrintDocument) -> Unit) : RecyclerView.Adapter<DocumentViewHolder>() {
    private val holders = ArrayList<DocumentViewHolder>()
    private var isDestroy = false //На всякий случай

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return DocumentViewHolder(view)
    }

    override fun getItemCount() = files.size

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val doc = files[position]

        holder.nameText.text = doc.name
        holder.createdAt.text = doc.createdAt.toLocalDateTime().toString("yyyy-MM-dd HH:mm")
        holder.contentCard.setOnClickListener { clickListener.invoke(doc) }

        holder.onNewDocument(doc)
    }

    fun setList(files: List<PrintDocument>) {
        this.files = files
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: DocumentViewHolder) {
        super.onViewRecycled(holder)
        holder.onDestroy()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        holders.forEach { it.onDestroy() }
    }

    fun onDestroy() {
        holders.forEach { it.onDestroy() }
        isDestroy = true
    }

}