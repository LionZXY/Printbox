package ru.lionzxy.printbox.view.print_files.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.view.print_files.presenter.PrintFilesPresenter

class PrintFilesFragment : MvpAppCompatFragment(), IPrintFilesView {
    @InjectPresenter
    lateinit var printFilesPresenter: PrintFilesPresenter

    companion object {
        val TAG = "printfiles"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print_file, container, false)
    }
}