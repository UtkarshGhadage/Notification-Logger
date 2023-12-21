package com.example.notificationlogger.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notificationlogger.DAO.LogDataDAO
import com.example.notificationlogger.Models.LogData

//@Database(entities = [LogDataModel::class], version = 1)
//abstract class LogDatabase : RoomDatabase() {
//
//    abstract fun logDao(): LogDataDAO
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: LogDatabase? = null
//
//        fun getInstance(context: Context): LogDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(context.applicationContext, LogDatabase::class.java,"room_database")
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance!!
//            }
//        }
//    }
//
//    private val roomCallback = object : Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            populateDatabase(INSTANCE!!)
//        }
//    }
//
//    private fun populateDatabase(db: LogDatabase) {
//        val logDAO = db.logDao()
//
//        logDAO.insertData(LogDataModel(1, "com.android.whatsapp", "Hi","Whatsapp", "1121"))
//        logDAO.insertData(LogDataModel(1, "com.android.FB", "Hi","FB", "1122"))
//        logDAO.insertData(LogDataModel(1, "com.android.snap", "Hi","Snap", "1123"))
//
//        }
//    }


@Database(entities = [LogData::class], version = 1)
abstract class LogDatabase : RoomDatabase() {
    abstract fun logDao(): LogDataDAO

    companion object {
        @Volatile
        private var INSTANCE: LogDatabase? = null

        fun getInstance(context: Context): LogDatabase {
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




//@Database(entities = [LogData::class], version = 1)
//abstract class LogDatabase : RoomDatabase() {
//    abstract fun logDAO(): LogDataDAO
//}
