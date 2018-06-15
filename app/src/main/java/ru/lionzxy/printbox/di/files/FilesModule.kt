package ru.lionzxy.printbox.di.files

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.data.stores.IPrintCartStore
import ru.lionzxy.printbox.interactor.files.FilesInteractor
import ru.lionzxy.printbox.interactor.files.IFilesInteractor
import ru.lionzxy.printbox.repository.files.FilesRepository
import ru.lionzxy.printbox.repository.files.IFilesRepository
import ru.lionzxy.printbox.repository.print.IPrintRepository
import ru.lionzxy.printbox.repository.print.PrintRepository

@Module
class FilesModule() {

    @FilesScope
    @Provides
    fun provideRepository(retrofit: Retrofit, context: Context): IFilesRepository {
        return FilesRepository(retrofit, context)
    }

    @FilesScope
    @Provides
    fun provideInteractor(repository: IFilesRepository, printRepository: IPrintRepository): IFilesInteractor {
        return FilesInteractor(repository, printRepository)
    }

    @FilesScope
    @Provides
    fun providePrintRepo(retrofit: Retrofit,
                          gson: Gson,
                          preferences: SharedPreferences,
                          cartStore: IPrintCartStore): IPrintRepository {
        return PrintRepository(retrofit, gson, preferences, cartStore)
    }
}