package ru.lionzxy.printbox.di.history

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.interactor.history.HistoryInteractor
import ru.lionzxy.printbox.interactor.history.IHistoryInteractor
import ru.lionzxy.printbox.repository.history.HistoryRepository
import ru.lionzxy.printbox.repository.history.IHistoryRepository

@Module
class HistoryModule() {

    @HistoryScope
    @Provides
    fun provideRepository(retrofit: Retrofit): IHistoryRepository {
        return HistoryRepository(retrofit)
    }

    @HistoryScope
    @Provides
    fun provideInteractor(repository: IHistoryRepository): IHistoryInteractor {
        return HistoryInteractor(repository)
    }
}
