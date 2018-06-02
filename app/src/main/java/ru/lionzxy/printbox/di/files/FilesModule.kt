package ru.lionzxy.printbox.di.files

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lionzxy.printbox.interactor.files.FilesInteractor
import ru.lionzxy.printbox.interactor.files.IFilesInteractor
import ru.lionzxy.printbox.repository.files.FilesRepository
import ru.lionzxy.printbox.repository.files.IFilesRepository

@Module
class FilesModule() {

    @FilesScope
    @Provides
    fun provideRepository(retrofit: Retrofit, context: Context): IFilesRepository {
        return FilesRepository(retrofit, context)
    }

    @FilesScope
    @Provides
    fun provideInteractor(repository: IFilesRepository): IFilesInteractor {
        return FilesInteractor(repository)
    }
}
