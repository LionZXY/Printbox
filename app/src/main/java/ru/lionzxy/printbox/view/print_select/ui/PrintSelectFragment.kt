package ru.lionzxy.printbox.view.print_select.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_print_select.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintOption
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.print_map.ui.PrintMapActivity
import ru.lionzxy.printbox.view.print_select.presenter.PrintSelectPresenter

class PrintSelectFragment : MvpAppCompatFragment(), IPrintSelectView {
    @InjectPresenter
    lateinit var printSelectPresenter: PrintSelectPresenter
    var selectionDialog: OptionSelectionDialog? = null

    companion object {
        val TAG = "printselect"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print_map.setOnClickListener { openPrintMapSelect() }
        file.setOnClickListener { printSelectPresenter.openFileChange() }
        select_twoside.setOnClickListener { printSelectPresenter.onClickSelectOption() }
        select_count.setOnValueChangedListener { _, _, newVal -> printSelectPresenter.onSelectCopiesCount(newVal) }
        ready.setOnClickListener { printSelectPresenter.onPrintClick() }
    }

    override fun onUpdateCartModel(cartModel: PrintCartModel) {
        cartModel.printPlace?.let { print_name.text = it.name }
        file_name.text = cartModel.printDocument?.name
        file_size.text = getString(R.string.option_file_size_template, cartModel.printDocument?.pagesCount)
        cartModel.printPlace?.optionDoublePage?.firstOrNull()?.let { select_twoside.text = it.name }
        cartModel.printOrder?.duplexOption?.let { select_twoside.text = it.name }
        select_color.isEnabled = cartModel.printPlace?.optionColor?.size ?: 0 > 1
        select_count.value = cartModel.printOrder?.copies ?: 1
        filled_perc.text = getString(R.string.options_filled_perc, (cartModel.printDocument?.colorPercent
                ?: 1f) * 100)
    }

    override fun priceLoadingShow() {
        price.setText(R.string.options_price_waiting)
    }

    override fun setPrice(totalPrice: Float, copies: Int) {
        price.text = getString(R.string.options_price, (totalPrice / copies), copies, totalPrice)
    }

    override fun printProgress(visible: Boolean) {
        ready.visibility = if (visible) View.GONE else View.VISIBLE
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun openDialog(visible: Boolean, items: List<PrintOption>) {
        if (!visible) {
            selectionDialog?.dismiss()
            selectionDialog = null
            return
        }

        val dialog = OptionSelectionDialog.createOptionDialog(items, {
            printSelectPresenter.onSelectPrintOption(it)
        })
        dialog.show(fragmentManager, OptionSelectionDialog.TAG)
        selectionDialog = dialog
    }

    override fun onError(resId: Int) {
        context?.toast(resId)
    }

    override fun openPrintMapSelect() {
        val intent = Intent(context, PrintMapActivity::class.java)
        startActivity(intent)
    }
}