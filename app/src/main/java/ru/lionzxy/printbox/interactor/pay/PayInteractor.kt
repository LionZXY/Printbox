package ru.lionzxy.printbox.interactor.pay

import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.OrderPay
import ru.lionzxy.printbox.repository.auth.IAuthRepository
import ru.lionzxy.printbox.repository.pay.IPayRepository

class PayInteractor(
        val payRepository: IPayRepository,
        val authRepository: IAuthRepository
) : IPayInteractor {
    override fun requestOrderPay(sum: Int): Single<OrderPay> {
        return payRepository.requestOrderPay(sum)
    }

    override fun getCurrentBalance(): Single<Double> {
        return authRepository.getUserAsync()
                .map { it.balance.toDouble() / 100 }
    }

    override fun getBalanceObservable(): Observable<Double> {
        return payRepository.getBalanceObservable()
    }
}