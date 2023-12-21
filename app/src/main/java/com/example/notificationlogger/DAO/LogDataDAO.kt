package com.example.notificationlogger.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notificationlogger.Models.LogData

@Dao
interface LogDataDAO {
    @Query("SELECT * FROM log_db ")
    fun getAll(): List<LogData>

    @Query("SELECT * FROM log_db WHERE id IN (:ids)")
    fun loadAllByIds(ids : LongArray): List<LogData>

    @Insert
    fun insertAll(data: LogData)

    @Delete
    fun delete(user: LogData)
}