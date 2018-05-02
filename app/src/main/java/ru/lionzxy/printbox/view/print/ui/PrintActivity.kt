package ru.lionzxy.printbox.view.print.ui

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.toolbar.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.view.print.presenter.PrintPresenter
import ru.lionzxy.printbox.view.print_map.ui.PrintMapActivity
import ru.lionzxy.printbox.view.print_select.ui.PrintSelectFragment

class PrintActivity : MvpAppCompatActivity(), IPrintView {
    @InjectPresenter
    lateinit var printPresenter: PrintPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction().replace(R.id.container, PrintSelectFragment()).commit()
        //openPrintMapSelect()
    }

    override fun openPrintMapSelect() {
        val intent = Intent(this, PrintMapActivity::class.java)
        startActivity(intent)
    }
}