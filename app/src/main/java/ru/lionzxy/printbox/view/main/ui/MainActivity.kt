package ru.lionzxy.printbox.view.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crashlytics.android.Crashlytics
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
import ru.lionzxy.printbox.utils.IActivityResultReciever
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.main.interfaces.IOnBackDelegator
import ru.lionzxy.printbox.view.main.interfaces.IRefreshStatusReciever
import ru.lionzxy.printbox.view.main.presenter.MainPresenter
import ru.lionzxy.printbox.view.print.ui.PrintFragment

class MainActivity : MvpAppCompatActivity(), IMainView, IRefreshStatusReciever {
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    lateinit var fragment: PrintFragment
    lateinit var drawer: Drawer

    companion object {
        const val REQUEST_MAP_CODE = 1
        const val REQUEST_FILE_CHOOSE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var tmpFragment = supportFragmentManager.findFragmentByTag(PrintFragment.TAG)

        if (tmpFragment == null) {
            tmpFragment = PrintFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.menu_container, tmpFragment, PrintFragment.TAG)
                    .commit()
        }
        fragment = tmpFragment as PrintFragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            if (it is IActivityResultReciever) {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun showRefreshStatus(visible: Boolean) {
        supportFragmentManager.fragments.forEach {
            if (it is IRefreshStatusReciever) {
                it.showRefreshStatus(visible)
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is IOnBackDelegator && it.onBack()) {
                return@onBackPressed
            }
        }
        mainPresenter.onClickBack()
    }


    override fun initDrawer(user: User) {
        var profile = ProfileDrawerItem().withName("${user.firstName} ${user.lastName}")
                .withEmail(user.email)
        if(user.avatar != null) {
            profile = profile.withIcon(user.avatar!!)
        }
        val header = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabled(false)
                .addProfiles(profile)
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

    override fun backPressForce() {
        super.onBackPressed()
    }

    override fun showOnBackToast() {
        toast(R.string.back_press_retry)
    }

    override fun showProgressBar(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        menu_container.visibility = if (visible) View.GONE else View.VISIBLE
    }

    private fun onClickDrawer(indentifier: Long): Boolean {
        when (indentifier) {
            100L -> mainPresenter.logout()
        }
        return true
    }
}