package ru.lionzxy.printbox.view.print_select.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.EXTRA_PRINTOPTION_LIST
import ru.lionzxy.printbox.data.model.PrintOption

class OptionSelectionDialog : DialogFragment() {
    var items: List<PrintOption> = emptyList()
        set(value) {
            field = value
            if (selectedElement == null) {
                selectedElement = items.firstOrNull()
            }
        }
    var selectedElement: PrintOption? = null
    var listener: ((item: PrintOption) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (savedInstanceState?.getParcelableArray(EXTRA_PRINTOPTION_LIST) as Array<PrintOption>?)?.let {
            items = it.toList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(EXTRA_PRINTOPTION_LIST, items.toTypedArray())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
                .setSingleChoiceItems(items.map { it.name }.toTypedArray(), 0, { _, which ->
                    selectedElement = items[which]
                }).setPositiveButton(android.R.string.ok, { _, _ ->
                    selectedElement?.let { listener?.invoke(it) }
                    dismiss()
                })

        return builder.create()
    }

    companion object {
        const val TAG = "selection_dialog"

        fun createOptionDialog(items: List<PrintOption>, listener: (item: PrintOption) -> Unit): OptionSelectionDialog {
            val dialog = OptionSelectionDialog()
            dialog.items = items
            dialog.listener = listener
            return dialog
        }
    }
}