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
import ru.lionzxy.printbox.view.print_map.ui.PrintMapActivity
import ru.lionzxy.printbox.view.print_select.presenter.PrintSelectPresenter

class PrintSelectFragment : MvpAppCompatFragment(), IPrintSelectView {
    @InjectPresenter
    lateinit var printSelectPresenter: PrintSelectPresenter

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
    }

    override fun onUpdateCartModel(cartModel: PrintCartModel) {
        cartModel.printPlace?.let { print_name.text = it.name }
        file_name.text = cartModel.printDocument?.name
        file_size.text = getString(R.string.option_file_size_template, cartModel.printDocument?.pagesCount)
    }

    override fun openPrintMapSelect() {
        val intent = Intent(context, PrintMapActivity::class.java)
        startActivity(intent)
    }
}