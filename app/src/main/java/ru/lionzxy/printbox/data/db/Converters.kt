package ru.lionzxy.printbox.data.db

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime {
        return if (value != null) DateTime(value) else DateTime()
    }

    @TypeConverter
    fun toTimestamp(time: DateTime?): Long {
        return (time ?: DateTime()).millis
    }

}