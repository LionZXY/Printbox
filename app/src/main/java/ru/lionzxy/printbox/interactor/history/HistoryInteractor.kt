package ru.lionzxy.printbox.interactor.history

import io.reactivex.Single
import ru.lionzxy.printbox.data.model.PrintHistory
import ru.lionzxy.printbox.repository.history.IHistoryRepository

class HistoryInteractor(
        val historyRepository: IHistoryRepository
) : IHistoryInteractor {
    override fun getHistory(): Single<List<PrintHistory>> {
        return historyRepository.getHistory()
    }

    override fun getHistoryById(id: Int): Single<PrintHistory> {
        return historyRepository.getHistoryById(id)
    }
}