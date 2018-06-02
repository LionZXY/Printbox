package ru.lionzxy.printbox.view.print_files.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_print_file.*
import kotlinx.android.synthetic.main.item_file_select.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.PrintDocument
import ru.lionzxy.printbox.utils.IActivityResultReciever
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.lionzxy.printbox.view.print_files.presenter.PrintFilesPresenter


class PrintFilesFragment : MvpAppCompatFragment(), IPrintFilesView, IActivityResultReciever {
    @InjectPresenter
    lateinit var printFilesPresenter: PrintFilesPresenter
    lateinit var rxPermission: RxPermissions
    val adapter by lazy { DocumentAdapter(emptyList()) }
    private lateinit var swipeHelper: SwipeOpenItemTouchHelper
    lateinit var progressDialog: ProgressDialog

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
        file_add.setOnClickListener { openFilePicker() }
        rxPermission = RxPermissions(this.activity!!)
        progressDialog = ProgressDialog(this.activity!!)
        progressDialog.setTitle(R.string.files_upload_progress)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setCancelable(false)
    }

    fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    MainActivity.REQUEST_FILE_CHOOSE)
        } catch (ex: android.content.ActivityNotFoundException) {
            context?.toast(R.string.files_select_notfound)
        }
    }

    override fun showProgres(visible: Boolean, current: Int, total: Int) {
        if(visible) {
            progressDialog.show()
        } else {
            progressDialog.hide()
        }

        progressDialog.max = total
        progressDialog.progress = current
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_FILE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return

            printFilesPresenter.onFileUpload(uri)
        }
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