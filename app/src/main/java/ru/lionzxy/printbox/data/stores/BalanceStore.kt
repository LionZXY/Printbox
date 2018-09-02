package ru.lionzxy.printbox.data.stores

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.lionzxy.printbox.repository.auth.PREF_BALANCE

class BalanceStore(preferences: SharedPreferences): IBalanceStore {
    private val subject = BehaviorSubject.create<Double>()

    init {
        subject.onNext(preferences.getInt(PREF_BALANCE, 0).toDouble() / 100)
    }

    override fun getBalanceObserver(): Observable<Double> {
        return subject
    }

    override fun setBalance(balance: Double) {
        subject.onNext(balance)
    }
}
