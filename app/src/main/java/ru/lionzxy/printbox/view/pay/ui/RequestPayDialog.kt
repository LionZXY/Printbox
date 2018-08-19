package ru.lionzxy.printbox.view.pay.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.dialog_pay_request.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.utils.toast

interface OnRequestPayDialogResult {
    fun onCancel() {}
    fun onResult(balance: Int) {}
}

class RequestPayDialog : AppCompatDialogFragment() {
    companion object {
        const val TAG = "pay_request_sum"
    }

    private var listener: OnRequestPayDialogResult? = null
    private var successful = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity!!)
                .inflate(R.layout.dialog_pay_request, null, false)
        val dialog = AlertDialog.Builder(activity!!, R.style.AppThemeDialog)
                .setTitle(R.string.pay_request_title)
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create()
        dialog.setOnShowListener { _ ->
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (request_sum.text.isNullOrEmpty()) {
                    context!!.toast(R.string.pay_request_not_empty)
                    return@setOnClickListener
                }
                val result = request_sum.text.toString().toIntOrNull()
                if (result == null) {
                    context!!.toast(R.string.pay_request_not_empty)
                    return@setOnClickListener
                }

                if (result <= context!!.resources.getInteger(R.integer.pay_minimal)) {
                    context!!.toast(R.string.pay_large_than)
                    return@setOnClickListener
                }

                successful = true
                listener?.onResult(result)

                dismiss()
            }
        }
        onViewCreated(view, savedInstanceState)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val editText = view.findViewById<EditText>(R.id.request_sum)
        editText.setSelection(editText.length())
    }

    override fun getView(): View? {
        return dialog.findViewById(R.id.frame)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if (!successful) {
            listener?.onCancel()
        }
    }

    fun setOnRequestPayDialogResult(listener: OnRequestPayDialogResult): RequestPayDialog {
        this.listener = listener
        return this
    }
}