package ru.lionzxy.printbox.di.print

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.interactor.print.PrintInteractor
import ru.lionzxy.printbox.repository.print.IPrintRepository
import ru.lionzxy.printbox.repository.print.PrintRepository

@Module
class PrintModule() {

    @PrintScope
    @Provides
    fun provideRepository(retrofit: Retrofit): IPrintRepository {
        return PrintRepository(retrofit)
    }

    @PrintScope
    @Provides
    fun provideInteractor(repository: IPrintRepository): IPrintInteractor {
        return PrintInteractor(repository)
    }
}
