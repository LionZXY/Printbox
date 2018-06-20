package ru.lionzxy.printbox.view.print_history.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_print_history.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.main.interfaces.IRefreshReciever
import ru.lionzxy.printbox.view.main.interfaces.IRefreshStatusReciever
import ru.lionzxy.printbox.view.print_history.presenter.PrintHistoryPresenter

class PrintHistoryFragment : MvpAppCompatFragment(), IPrintHistoryView, IRefreshReciever {
    @InjectPresenter
    lateinit var printHistoryPresenter: PrintHistoryPresenter
    val adapter by lazy { produceAdapter() }

    companion object {
        val TAG = "printhistory"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        history.adapter = adapter
        history.isNestedScrollingEnabled = false
        history.layoutManager = LinearLayoutManager(context)
    }

    override fun onError(resId: Int) {
        context?.toast(resId)
    }

    override fun loadHistory(history: List<PrintHistory>) {
        adapter.setList(history)
    }

    override fun update() {
        printHistoryPresenter.loadHistory(true)
    }

    override fun showLoad(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        history.visibility = if (visible) View.GONE else View.VISIBLE

        if (!visible) {
            val tmp = activity
            if (tmp is IRefreshStatusReciever) {
                tmp.showRefreshStatus(false)
            }
        }
    }

    private fun produceAdapter(): HistoryAdapter {
        return HistoryAdapter(emptyList(), {
            context?.toast("Что лучше делать на клик? Есть вариант кинуть пользователя на экран повторения заказа. Можно еще перед этим диалог показывать. Типо вы точно хотите поторить заказ.")
        })
    }
}