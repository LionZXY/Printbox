package ru.lionzxy.printbox.view.print_select.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.view.print_select.presenter.PrintSelectPresenter

class PrintSelectFragment : MvpAppCompatFragment(), IPrintSelectView {
    @InjectPresenter
    lateinit var printSelectPresenter: PrintSelectPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}