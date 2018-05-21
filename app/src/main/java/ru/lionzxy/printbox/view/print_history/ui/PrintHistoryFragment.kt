package ru.lionzxy.printbox.view.print_history.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.view.print_history.presenter.PrintHistoryPresenter

class PrintHistoryFragment : MvpAppCompatFragment(), IPrintHistoryView {
    @InjectPresenter
    lateinit var printHistoryPresenter: PrintHistoryPresenter

    companion object {
        val TAG =  "printhistory"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print_history, container, false)
    }
}