package ru.lionzxy.printbox.repository.pay

import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.OrderPay

interface IPayRepository {
    fun requestOrderPay(sum: Int): Single<OrderPay>
    fun getBalanceObservable(): Observable<Double>
}