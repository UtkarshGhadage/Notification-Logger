package com.example.notificationlogger.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notificationlogger.DAO.LogDataDAO
import com.example.notificationlogger.Models.LogDataModel

@Database(entities = [LogDataModel::class], version = 1)
abstract class LogDatabase : RoomDatabase() {

    abstract fun logDao(): LogDataDAO

    companion object {
        const val DATABASE_NAME = "log_database.db"

        @Volatile
        private var INSTANCE: LogDatabase? = null

        fun getInstance(context: Context): LogDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, LogDatabase::class.java, DATABASE_NAME)
                        .build()
                    INSTANCE = instance
                }
                return instance!!
            }
        }
    }

    private val roomCallback = object : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            populateDatabase(INSTANCE!!)
        }
    }

    private fun populateDatabase(db: LogDatabase) {
        val logDAO = db.logDao()

        logDAO.insertData(LogDataModel(1, "com.android.whatsapp", "Hi","Whatsapp", "1121"))
        logDAO.insertData(LogDataModel(1, "com.android.FB", "Hi","FB", "1122"))
        logDAO.insertData(LogDataModel(1, "com.android.snap", "Hi","Snap", "1123"))

        }
    }

