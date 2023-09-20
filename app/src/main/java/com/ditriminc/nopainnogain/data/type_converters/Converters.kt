package com.ditriminc.nopainnogain.data.type_converters

import androidx.room.TypeConverter
import java.util.Date


class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromIdListToString(list: ArrayList<Long>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromStringToIdList(str: String?): ArrayList<Long> {
        return if (str != "") {
            val list: ArrayList<Long> = ArrayList()
            str?.split(",")?.forEach { list.add(it.toLong())}
            list
        } else {
            ArrayList()
        }
    }


}