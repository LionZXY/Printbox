package ru.lionzxy.printbox.repository.history

import io.reactivex.Observable
import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintHistory

interface IHistoryRepository {
    fun getHistory(): Single<List<PrintHistory>>
}