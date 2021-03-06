package ru.lionzxy.printbox.view.register.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Nikita Kulikov <nikita@kulikof.ru>
 * @project SecretBook
 * @date 06.03.18
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface IRegisterView : MvpView {
    fun onLoading(isEnable: Boolean)
    @StateStrategyType(SkipStrategy::class)
    fun onError(resId: Int)

    fun showPasswordError(resError: Int)
    fun showEmailError(resError: Int)
    fun hidePasswordError()
    fun hideEmailError()
    fun buttonActive(active: Boolean)

    fun onAuth()
}