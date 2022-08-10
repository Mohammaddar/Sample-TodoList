package com.example.todolist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todolist.data.local.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAll(): Flow<List<Task>>

    @Query("DELETE FROM task_table")
    suspend fun clearAll(): Int

    @Query("DELETE FROM task_table WHERE isCompleted=1")
    suspend fun clearAllCompleted(): Int

    @Query("DELETE FROM task_table WHERE id=:id")
    suspend fun remove(id: Int): Int

    @Query("UPDATE task_table SET isCompleted=:isCompleted WHERE id=:taskId")
    suspend fun update(taskId: Int, isCompleted: Boolean): Int

    @Query("UPDATE task_table SET position=:position WHERE id=:taskId")
    suspend fun update(taskId: Int, position: Int): Int

    @Query("SELECT MAX(position) From task_table")
    suspend fun getMaxPosition(): Int
}

