package com.example.notificationlogger

import DataModel
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.notificationlogger.Database.LogDatabase
import com.example.notificationlogger.Retrofit.RetrofitBuilder
import com.example.notificationlogger.Retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltWorker
class DataSavingWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    @Inject
    lateinit var mRetrofit : RetrofitBuilder
    override fun doWork(): Result {
        val ctx = NotificationLoggerService()

        if(ctx != null && ::mRetrofit.isInitialized) {

            val instance = LogDatabase.getInstance(ctx)
            val dataDao = instance.logDao()

//            val dataApi = RetrofitBuilder.getInstance().create(RetrofitInterface::class.java)
            val dataApi = mRetrofit.getInstance().create(RetrofitInterface::class.java)

            var logDataModel = dataDao.getAll()
            for (log in logDataModel) {
                var id: Long = log.id
                val timestamp: String? = log.timestamp
                val title: String? = log.title
                val text: String? = log.text
                val packageName: String? = log.packageName

                runBlocking {
                    try {
                        withContext(Dispatchers.IO) {
                            dataApi.insertData(DataModel(id, timestamp, title, text, packageName))
                        }
                        // Data insertion successful
                    } catch (e: Exception) {
                        // Handle the error if insertion fails
                        e.printStackTrace()
                    }
                }
            }
        }
        return Result.success()
    }
}
