package ru.lionzxy.printbox.view.main.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
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
import ru.lionzxy.printbox.utils.IActivityResultReciever
import ru.lionzxy.printbox.utils.navigation.MainScreenNavigator
import ru.lionzxy.printbox.utils.navigation.PrintBoxRouter
import ru.lionzxy.printbox.utils.toast
import ru.lionzxy.printbox.view.main.interfaces.IOnBackDelegator
import ru.lionzxy.printbox.view.main.interfaces.IRefreshStatusReciever
import ru.lionzxy.printbox.view.main.presenter.MainPresenter
import ru.lionzxy.printbox.view.pay.ui.PayFragment
import ru.lionzxy.printbox.view.print.ui.PrintFragment

class MainActivity : MvpAppCompatActivity(), IMainView, IRefreshStatusReciever {
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    lateinit var drawer: Drawer

    private val idToTag = HashMap<Long, String>()
    private val tagToFragmentClazz = HashMap<String, Class<out Fragment>>()
    private val tagToFragment = HashMap<String, Fragment>()

    companion object {
        const val REQUEST_MAP_CODE = 1
        const val REQUEST_FILE_CHOOSE = 2

        const val ID_PRINTFRAGMENT = 0L
        const val ID_PAYFRAGMENT = 1L
        const val ID_LOGOUT = 100L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addFragmentToList(ID_PRINTFRAGMENT, PrintFragment.TAG, PrintFragment::class.java)
        addFragmentToList(ID_PAYFRAGMENT, PayFragment.TAG, PayFragment::class.java)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null || !intent.hasExtra(MainScreenNavigator.EXTRA_SCREENKEY)) {
            return
        }

        val screenKey = intent.getStringExtra(MainScreenNavigator.EXTRA_SCREENKEY)
        val data = if (intent.hasExtra(MainScreenNavigator.EXTRA_DATA)) intent.getBundleExtra(MainScreenNavigator.EXTRA_DATA) else null
        if (screenKey.startsWith(PrintBoxRouter.MAIN)) {
            mainPresenter.openScreen(screenKey, data)
            return
        }
    }

    override fun openFragmentWithId(indentifier: Long, data: Bundle?) {
        drawer.closeDrawer()
        if (drawer.currentSelection != indentifier) {
            drawer.setSelection(indentifier, false)
        }
        val tag = idToTag[indentifier] ?: return
        val tmpFragment = supportFragmentManager.findFragmentByTag(tag) ?: tagToFragment[tag]
        ?: tagToFragmentClazz[tag]!!.newInstance()

        if (tmpFragment != null && !tmpFragment.isAdded) {
            var transaction = supportFragmentManager.beginTransaction()
            var hasFragmentActive = false
            supportFragmentManager.fragments.forEach {
                if (it != tmpFragment) {
                    transaction = transaction.remove(it)
                } else hasFragmentActive = true
            }
            if (!hasFragmentActive) {
                transaction = transaction.add(R.id.menu_container, tmpFragment, tag)
            }
            transaction.commit()

            tagToFragment[tag] = tmpFragment
        }
        if (data != null) {
            tmpFragment.arguments = data
        }
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
        if (user.avatar != null) {
            profile = profile.withIcon(user.avatar!!)
        }
        val header = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabled(false)
                .addProfiles(profile)
                .withOnAccountHeaderListener { _, _, _ -> false }
                .build()

        val main = PrimaryDrawerItem()
                .withIdentifier(0)
                .withName(R.string.home_title) // Костыль. Вот тут очень странный баг на стороне либы. По хорошему надо зарепортить, но лень
                .withIcon(R.drawable.ic_action_home)

        val pay = PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.pay_title) // Костыль. Вот тут очень странный баг на стороне либы. По хорошему надо зарепортить, но лень
                .withIcon(R.drawable.ic_action_pay)

        val exitDrawer = PrimaryDrawerItem()
                .withIdentifier(100)
                .withIcon(R.drawable.ic_acc_exit)
                .withName(R.string.auth_logout)

        drawer = DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        main,
                        pay,
                        DividerDrawerItem(),
                        exitDrawer
                )
                .withSelectedItem(0)
                .withKeepStickyItemsVisible(false)
                .withOnDrawerItemClickListener { _, _, drawerItem -> mainPresenter.onClickDrawer(drawerItem.identifier) }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_balance)?.actionView?.setOnClickListener {
            mainPresenter.openScreen(PrintBoxRouter.MAIN_PAY, null)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun addFragmentToList(indentifier: Long, tag: String, fragmentClazz: Class<out Fragment>) {
        idToTag[indentifier] = tag
        tagToFragmentClazz[tag] = fragmentClazz
    }
}