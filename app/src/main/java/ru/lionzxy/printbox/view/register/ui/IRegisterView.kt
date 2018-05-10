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

    fun showLoginError(resError: Int)
    fun showPasswordError(resError: Int)
    fun showSecondPasswordError(resError: Int)
    fun showEmailError(resError: Int)
    fun hideLoginError()
    fun hidePasswordError()
    fun hideEmailError()
    fun hideSecondPasswordError()
    fun buttonActive(active: Boolean)

    fun onAuth()
}