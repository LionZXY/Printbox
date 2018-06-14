package ru.lionzxy.printbox.view.print.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintCartStage
import ru.lionzxy.printbox.view.print.presenter.PrintPresenter
import ru.lionzxy.printbox.view.print_files.ui.PrintFilesFragment
import ru.lionzxy.printbox.view.print_history.ui.PrintHistoryFragment
import ru.lionzxy.printbox.view.print_select.ui.PrintSelectFragment

class PrintFragment : MvpAppCompatFragment(), IPrintView {
    @InjectPresenter
    lateinit var printPresenter: PrintPresenter
    var fileFragment: PrintFilesFragment? = null
    var optionFragment: PrintSelectFragment? = null
    var historyFragment: PrintHistoryFragment? = null
    var openedFragment: Fragment? = null

    companion object {
        val TAG = "printfragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fileFragment = fragmentManager?.findFragmentByTag(PrintFilesFragment.TAG) as PrintFilesFragment?
        optionFragment = fragmentManager?.findFragmentByTag(PrintSelectFragment.TAG) as PrintSelectFragment?
        historyFragment = fragmentManager?.findFragmentByTag(PrintHistoryFragment.TAG) as PrintHistoryFragment?

        if (fileFragment == null) {
            fileFragment = PrintFilesFragment()
        }

        if (optionFragment == null) {
            optionFragment = PrintSelectFragment()
        }

        if (historyFragment == null) {
            historyFragment = PrintHistoryFragment()
        }
    }

    override fun setCartModel(cartModel: PrintCartModel) {
        openFragmentByState(cartModel)
    }

    private fun openFragmentByState(cartModel: PrintCartModel) {
        val currentTag: String
        val currentFragment: Fragment?
        when (cartModel.stage) {
            PrintCartStage.SELECTION_FILE -> {
                currentTag = PrintFilesFragment.TAG
                currentFragment = fileFragment
            }
            PrintCartStage.OPTIONS -> {
                currentTag = PrintSelectFragment.TAG
                currentFragment = optionFragment
            }
            PrintCartStage.HISTORY -> {
                currentTag = PrintHistoryFragment.TAG
                currentFragment = historyFragment
            }
        }

        if (currentFragment == openedFragment) {
            return
        }

        openedFragment = currentFragment

        currentFragment?.let {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.container, currentFragment, currentTag)
                    ?.commit()
        }
    }
}