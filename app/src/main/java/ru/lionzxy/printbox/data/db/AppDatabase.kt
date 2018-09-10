package ru.lionzxy.printbox.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.lionzxy.printbox.data.model.PrintDocument

@Database(entities = [(PrintDocument::class)], version = 6)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFileDao(): FileDAO
}