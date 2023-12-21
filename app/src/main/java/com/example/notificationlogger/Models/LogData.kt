package com.example.notificationlogger.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_db")
data class LogData(

    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo("timestamp")
    val timestamp: String?,
    @ColumnInfo("androidTitle")
    val title: String?,
    @ColumnInfo("androidText")
    val text: String? ,
    @ColumnInfo("pkg")
    val packageName: String? ,

)
