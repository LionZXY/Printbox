package ru.lionzxy.printbox.view.print.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_print.*
import kotlinx.android.synthetic.main.item_menu.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintCartStage
import ru.lionzxy.printbox.view.main.interfaces.IOnBackDelegator
import ru.lionzxy.printbox.view.main.interfaces.IRefreshReciever
import ru.lionzxy.printbox.view.main.interfaces.IRefreshStatusReciever
import ru.lionzxy.printbox.view.print.presenter.PrintPresenter
import ru.lionzxy.printbox.view.print_files.ui.PrintFilesFragment
import ru.lionzxy.printbox.view.print_history.ui.PrintHistoryFragment
import ru.lionzxy.printbox.view.print_select.ui.PrintSelectFragment

class PrintFragment : MvpAppCompatFragment(), IPrintView, IRefreshStatusReciever, IOnBackDelegator {
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

        menu_one.setOnClickListener { printPresenter.openState(PrintCartStage.SELECTION_FILE) }
        menu_two.setOnClickListener { printPresenter.openState(PrintCartStage.OPTIONS) }
        menu_three.setOnClickListener { printPresenter.openState(PrintCartStage.HISTORY) }
        swiperefresh.setOnRefreshListener {
            val fragm = openedFragment
            if (fragm is IRefreshReciever) {
                fragm.update()
            }
        }
    }

    override fun onBack(): Boolean {
        val curFragment = openedFragment
        if (curFragment is IOnBackDelegator) {
            return curFragment.onBack()
        }
        return false
    }

    override fun showRefreshStatus(visible: Boolean) {
        swiperefresh.isRefreshing = visible
    }

    override fun setCartModel(cartModel: PrintCartModel) {
        openFragmentByState(cartModel)
        openHeaderByState(cartModel)
    }

    private fun openHeaderByState(cartModel: PrintCartModel) {
        var background = R.drawable.menu_button_disactive
        var active = false
        if (cartModel.printOrder != null) {
            background = R.drawable.menu_button_active
            active = true
        }
        menu_two.background = ContextCompat.getDrawable(context!!, background)
        menu_two.isClickable = active
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

        swiperefresh.isEnabled = openedFragment is IRefreshReciever

        val fm = childFragmentManager
        if (currentFragment == null || fm == null) {
            return
        }

        var transaction = fm.beginTransaction()
        var hasFragmentActive = false
        fm.fragments.forEach {
            if (it != currentFragment) {
                transaction = transaction.remove(it)
            } else hasFragmentActive = true
        }
        if (!hasFragmentActive) {
            transaction = transaction.add(R.id.print_container, currentFragment, currentTag)
        }
        transaction.commit()
    }
}