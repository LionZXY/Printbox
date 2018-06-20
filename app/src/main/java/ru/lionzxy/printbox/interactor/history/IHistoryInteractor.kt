package ru.lionzxy.printbox.interactor.history

import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintHistory

interface IHistoryInteractor {
    fun getHistory(): Single<List<PrintHistory>>
}