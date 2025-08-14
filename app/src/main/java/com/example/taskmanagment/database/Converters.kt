package com.example.taskmanagment.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromListOfStrings(list: List<String>?): String {
        return if (list.isNullOrEmpty()) "" else list.joinToString(",")
    }

    @TypeConverter
    fun toListOfStrings(str: String?): List<String> {
        return if (str.isNullOrBlank())
            listOf()
        else
            str.split(",")
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}