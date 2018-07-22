package ru.lionzxy.printbox.di.app

import dagger.Module
import dagger.Provides
import ru.lionzxy.printbox.data.db.AppDatabase
import ru.lionzxy.printbox.data.db.FileDAO
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun getFileDao(appDatabase: AppDatabase): FileDAO {
        return appDatabase.getFileDao()
    }
}