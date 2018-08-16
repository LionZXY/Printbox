package ru.lionzxy.printbox.repository.pay

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.AuthApi
import ru.lionzxy.printbox.data.model.OrderPay
import ru.lionzxy.printbox.data.model.OrderPayRequest

class PayRepository(retrofit: Retrofit) : IPayRepository {
    val api = retrofit.create(AuthApi::class.java)

    override fun requestOrderPay(sum: Int): Single<OrderPay> {
        return api.requestOrder(OrderPayRequest(sum))
                .subscribeOn(Schedulers.io())
    }
}