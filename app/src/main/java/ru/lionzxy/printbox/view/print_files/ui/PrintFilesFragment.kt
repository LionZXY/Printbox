package ru.lionzxy.printbox.view.print_files.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_print_file.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.print_files.presenter.PrintFilesPresenter

class PrintFilesFragment : MvpAppCompatFragment(), IPrintFilesView {
    @InjectPresenter
    lateinit var printFilesPresenter: PrintFilesPresenter
    val adapter by lazy { DocumentAdapter(emptyList()) }
    private lateinit var swipeHelper: SwipeOpenItemTouchHelper

    companion object {
        val TAG = "printfiles"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_print_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printFilesPresenter.loadList()
        files.adapter = adapter
        files.isNestedScrollingEnabled = false
        files.layoutManager = LinearLayoutManager(context)
        swipeHelper = SwipeOpenItemTouchHelper(SwipeOpenItemTouchHelper.SimpleCallback(SwipeOpenItemTouchHelper.START
                or SwipeOpenItemTouchHelper.END))
        swipeHelper.attachToRecyclerView(files)
    }

    override fun setFiles(docs: List<PrintDocument>) {
        adapter.setList(docs)
        swipeHelper.attachToRecyclerView(files)
    }

    override fun showLoading(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        files.visibility = if (visible) View.GONE else View.VISIBLE
    }

    override fun onError(resId: Int) {
        context?.toast(resId)
    }
}