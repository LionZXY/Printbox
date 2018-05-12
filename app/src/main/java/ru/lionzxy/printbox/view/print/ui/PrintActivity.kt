package ru.lionzxy.printbox.view.print.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.lionzxy.printbox.R
import ru.lionzxy.printbox.data.model.User
import ru.lionzxy.printbox.view.print.presenter.PrintPresenter
import ru.lionzxy.printbox.view.print_map.ui.PrintMapActivity
import ru.lionzxy.printbox.view.print_select.ui.PrintSelectFragment

class PrintActivity : MvpAppCompatActivity(), IPrintView {
    @InjectPresenter
    lateinit var printPresenter: PrintPresenter
    lateinit var drawer: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction().replace(R.id.container, PrintSelectFragment()).commit()
        //openPrintMapSelect()
    }

    override fun initDrawer(user: User) {
        val header = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabled(false)
                .addProfiles(
                        ProfileDrawerItem().withName("${user.firstName} ${user.lastName}")
                                .withEmail(user.email))
                .withOnAccountHeaderListener { _, _, _ -> false }
                .build()

        val exitDrawer = PrimaryDrawerItem()
                .withIdentifier(100)
                .withIcon(R.drawable.ic_acc_exit)
                .withName(R.string.auth_logout)

        drawer = DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        DividerDrawerItem(),
                        exitDrawer
                )
                .withKeepStickyItemsVisible(false)
                .withOnDrawerItemClickListener { _, _, drawerItem -> onClickDrawer(drawerItem.identifier) }
                .build()
    }


    override fun showProgressBar(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        content.visibility = if (visible) View.GONE else View.VISIBLE
    }

    private fun onClickDrawer(indentifier: Long): Boolean {
        when (indentifier) {
            100L -> printPresenter.logout()
        }
        return true
    }

    override fun openPrintMapSelect() {
        val intent = Intent(this, PrintMapActivity::class.java)
        startActivity(intent)
    }
}