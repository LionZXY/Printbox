package ru.lionzxy.printbox.di.print

import dagger.Module
import dagger.Provides
import ru.lionzxy.printbox.interactor.print.PrintInteractor
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.repository.print.PrintRepository
import ru.lionzxy.printbox.repository.print.IPrintRepository

@Module
class PrintModule() {

    @PrintScope
    @Provides
    fun provideRepository(): IPrintRepository {
        return PrintRepository()
    }

    @PrintScope
    @Provides
    fun provideInteractor(repository: IPrintRepository): IPrintInteractor {
        return PrintInteractor(repository)
    }
}
