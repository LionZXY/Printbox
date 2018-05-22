package ru.lionzxy.printbox.di.files

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
    fun provideRepository(retrofit: Retrofit): IFilesRepository {
        return FilesRepository(retrofit)
    }

    @FilesScope
    @Provides
    fun provideInteractor(repository: IFilesRepository): IFilesInteractor {
        return FilesInteractor(repository)
    }
}
