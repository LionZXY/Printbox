package ru.lionzxy.printbox.di.print

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.stores.IBalanceStore
import ru.lionzxy.printbox.data.stores.IPrintCartStore
import ru.lionzxy.printbox.di.pay.PayScope
import ru.lionzxy.printbox.interactor.print.IPrintInteractor
import ru.lionzxy.printbox.interactor.print.PrintInteractor
import ru.lionzxy.printbox.repository.pay.IPayRepository
import ru.lionzxy.printbox.repository.pay.PayRepository
import ru.lionzxy.printbox.repository.print.IPrintRepository
import ru.lionzxy.printbox.repository.print.PrintRepository

@Module
class PrintModule {

    @PrintScope
    @Provides
    fun provideRepository(retrofit: Retrofit,
                          gson: Gson,
                          preferences: SharedPreferences,
                          cartStore: IPrintCartStore): IPrintRepository {
        return PrintRepository(retrofit, gson, preferences, cartStore)
    }

    @PrintScope
    @Provides
    fun provideInteractor(repository: IPrintRepository, payRepository: IPayRepository): IPrintInteractor {
        return PrintInteractor(repository, payRepository)
    }

    @PrintScope
    @Provides
    fun providePayRepository(retrofit: Retrofit, balanceStore: IBalanceStore): IPayRepository {
        return PayRepository(retrofit, balanceStore)
    }

}
