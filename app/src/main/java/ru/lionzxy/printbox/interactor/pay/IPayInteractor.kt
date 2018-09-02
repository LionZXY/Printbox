package ru.lionzxy.printbox.interactor.pay

import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.OrderPay

interface IPayInteractor {
    fun requestOrderPay(sum: Int): Single<OrderPay>
    fun getCurrentBalance(): Single<Double>
    fun getBalanceObservable(): Observable<Double>
}