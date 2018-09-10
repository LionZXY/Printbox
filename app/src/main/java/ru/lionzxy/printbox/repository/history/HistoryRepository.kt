package ru.lionzxy.printbox.repository.history

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.api.HistoryApi
import ru.lionzxy.printbox.data.model.PrintHistory

class HistoryRepository(retrofit: Retrofit) : IHistoryRepository {
    val historyApi = retrofit.create(HistoryApi::class.java)

    override fun getHistory(): Single<List<PrintHistory>> {
        return historyApi.getHistory().subscribeOn(Schedulers.io())
                .singleOrError()
                .map { it.results }
    }

    override fun getHistoryById(id: Int): Single<PrintHistory> {
        return historyApi.getHistoryByid(id)
                .subscribeOn(Schedulers.io())
    }
}