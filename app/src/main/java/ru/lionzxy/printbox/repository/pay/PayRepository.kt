package ru.lionzxy.printbox.repository.pay

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.AuthApi
import ru.lionzxy.printbox.data.model.OrderPay
import ru.lionzxy.printbox.data.model.OrderPayRequest
import ru.lionzxy.printbox.data.stores.IBalanceStore

class PayRepository(retrofit: Retrofit, private val balanceStore: IBalanceStore) : IPayRepository {
    val api = retrofit.create(AuthApi::class.java)

    override fun requestOrderPay(sum: Int): Single<OrderPay> {
        return api.requestOrder(OrderPayRequest(sum))
                .subscribeOn(Schedulers.io())
    }

    override fun getBalanceObservable(): Observable<Double> {
        return balanceStore.getBalanceObserver()
    }
}