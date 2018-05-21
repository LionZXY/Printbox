package ru.lionzxy.printbox.view.main.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.lionzxy.printbox.data.model.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface IMainView : MvpView {
    fun showProgressBar(visible: Boolean)
    fun initDrawer(user: User)
}
