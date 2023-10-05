package com.ditriminc.nopainnogain.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String?,
    @ColumnInfo(name = "group_id")
    val groupId: Long?,
    @ColumnInfo(name = "previous_results")
    val previousResultsIds: ArrayList<Long> = ArrayList(),
    @ColumnInfo(name = "previous_comment")
    val previousComment : String?,
    @ColumnInfo(name = "previous_reaction")
    val previousReaction : Int
)


