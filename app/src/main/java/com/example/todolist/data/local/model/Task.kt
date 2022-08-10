package com.example.todolist.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,

    @ColumnInfo
    var text:String?,

    @ColumnInfo
    var isCompleted:Boolean,

    @ColumnInfo
    var position:Int
)

