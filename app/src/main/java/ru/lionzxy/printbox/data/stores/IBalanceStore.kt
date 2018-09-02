package ru.lionzxy.printbox.data.stores

import io.reactivex.Observable

interface IBalanceStore {
    fun getBalanceObserver(): Observable<Double>
    fun setBalance(balance: Double)
}