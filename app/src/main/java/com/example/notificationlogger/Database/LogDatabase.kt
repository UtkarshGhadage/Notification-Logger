package com.example.notificationlogger.Database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notificationlogger.DAO.LogDataDAO
import com.example.notificationlogger.Models.LogData
import com.example.notificationlogger.NotificationLoggerService


@Database(entities = [LogData::class], version = 1)
abstract class LogDatabase : RoomDatabase() {
    abstract fun logDao(): LogDataDAO

    companion object {
        @Volatile
        private var INSTANCE: LogDatabase? = null

        fun getInstance(context: NotificationLoggerService): LogDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder( context,
                        LogDatabase::class.java,
                        "log_database"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
