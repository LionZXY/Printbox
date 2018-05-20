package ru.lionzxy.printbox.view.print_map.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintPlace

const val TYPE_INACTIVE = 0
const val TYPE_ACTIVE = 1

class PrintAdapter(private var printers: List<PrintPlace>) : RecyclerView.Adapter<PrintHolder>() {
    private var currentPrinter: PrintPlace? = null
    private var clickListener: ((PrintPlace) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrintHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_printer, parent, false)
        val holder = PrintHolder(view)
        if (viewType == TYPE_ACTIVE) {
            val color = ColorStateList.valueOf(ContextCompat.getColor(view.context, R.color.colorBackgroundDark))
            holder.cardView.setCardBackgroundColor(color)
        }
        return holder
    }

    override fun getItemCount() = printers.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PrintHolder, position: Int) {
        val printer = printers[position]
        holder.printName.text = printer.name
        holder.printStatusName.text = printer.statusName
        holder.printDescription.text = printer.placeDescription + printer.description
        holder.cardView.setOnClickListener { clickListener?.invoke(printer) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (printers[position] == currentPrinter) TYPE_ACTIVE else TYPE_INACTIVE
    }

    fun setList(printers: List<PrintPlace>) {
        this.printers = printers
        notifyDataSetChanged()
    }

    fun setListener(listener: ((PrintPlace) -> Unit)) {
        this.clickListener = listener
    }

    fun setCurrentPrint(currentPrinter: PrintPlace): Int {
        this.currentPrinter = currentPrinter
        val position = printers.indexOf(currentPrinter)
        notifyItemChanged(position)
        return position
    }
}

class PrintHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val printImage = rootView.findViewById<ImageView>(R.id.printer_image)
    val printName = rootView.findViewById<TextView>(R.id.printer_name)
    val printStatusImage = rootView.findViewById<ImageView>(R.id.printer_status_image)
    val printStatusName = rootView.findViewById<TextView>(R.id.printer_status)
    val printDescription = rootView.findViewById<TextView>(R.id.printer_description)
    val cardView = rootView.findViewById<CardView>(R.id.cardview)
}