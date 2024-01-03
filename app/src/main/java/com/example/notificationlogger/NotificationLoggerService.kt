package com.example.notificationlogger

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.notificationlogger.Database.LogDatabase
import com.example.notificationlogger.Models.LogData
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class NotificationLoggerService : NotificationListenerService() {


    private val TAG = "LOG.D"
    private val CHANNEL_ID = "notification_logger_channel"
    private val CHANNEL_NAME = "logchannel"

    override fun onCreate() {
        super.onCreate()
        Log.d("INVOCATION", "OncreateInitialize")
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        channel.description = "Notification logging service channel"
        notificationManager.createNotificationChannel(channel)
    }

    private fun startForegroundService() {


        Log.d("INVOCATION", "Foreground Service Invoked")
        val notificationIntent = Intent(this, NotificationLoggerService::class.java)
        val pendingIntent = PendingIntent.getService(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification Logger")
            .setContentText("Continuously logging notifications")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }


    val processedNotifications = HashSet<String>()

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        super.onNotificationPosted(sbn)

        createNotificationChannel()
        startForegroundService()

        var key = sbn.key

        if (!processedNotifications.contains(key)) {
            processedNotifications.add(key)

            Log.d("INVOCATION", "Notification Posted Service Invoked")
            val mNotification = sbn.notification
            val extras = mNotification.extras


            Log.d(TAG, "**Notification posted**")

            //Extras, SBN and Notification Props
            var mapExtras: String? = ""
            var mapSbn: String = ""
            var mapNotifs: String = ""

            try {

                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()



                mapExtras = Gson().toJson(extras)
                mapSbn = Gson().toJson(sbn)
                mapNotifs = Gson().toJson(mNotification)

            } catch (e: IllegalArgumentException) {
                Log.d(TAG, "IllegalArgumentException in JSON")
            }

//
//            Log.d(TAG, "1)***** Extras Props in json: ${mapExtras.toString()}  \"2)*****SBN Props in json: ${mapSbn.toString()}\" \"3)*****Notifs Props in json: ${mapNotifs.toString()}\"")

//            var json = mapNotifs.toString()
//            val length = json.length
//                var i = 0
//                while (i < length) {
//                    if (i + 1024 < length) Log.d(
//                        "JSON OUTPUT",
//                        json.substring(i, i + 1024)
//                    ) else Log.d("JSON OUTPUT", json.substring(i, length))
//                    i += 1024
//                }


            var jsonObjectNotifs: JSONObject?
            var whenTime: Long = 0
            try {
                //mapNoitifs
                jsonObjectNotifs = JSONObject(mapNotifs)
                whenTime = jsonObjectNotifs.getLong("when")
                Log.d(TAG, "timestamp from Nofifs: $whenTime")
            } catch (e: JSONException) {
                Log.d(TAG, "Exception in JSON")
            }


            // Extracting android.template from Extras

            var jsonObjectExtras: JSONObject? = JSONObject()

            try {
                jsonObjectExtras = JSONObject(mapExtras)
            } catch (e: JSONException) {
                Log.d(TAG, "Exception in JSON")
            }


            var textTemplate: String? = ""
            try {

                var templateValue: Any? =
                    jsonObjectExtras?.getJSONObject("mMap")?.getString("android.template")


                if (templateValue is String) {
                    textTemplate = templateValue
                } else if (templateValue is JSONObject) {
                    // It's an object, dig deeper to extract the "mText" value
                    textTemplate = templateValue.getString("mText")
                }
            } catch (e: JSONException) {
                Log.e("JSON_ERROR", "Error parsing JSON: $e")
            }

            // Extracting android.title from Extras

            var titleFinal: String? = ""
            try {
                var titleValue: Any? =
                    jsonObjectExtras?.getJSONObject("mMap")?.getString("android.title")


                if (titleValue is String) {
                    titleFinal = titleValue
                } else if (titleValue is JSONObject) {
                    // It's an object, dig deeper to extract the "mText" value
                    titleFinal = titleValue.getString("mText")

                }
            } catch (e: JSONException) {
                Log.e("JSON_ERROR", "Error parsing JSON: $e")
            }


            var jsonObjectPkg: JSONObject?
            var pkg: String? = ""

            try {
                jsonObjectPkg = JSONObject(mapSbn)
                pkg = jsonObjectPkg.getString("pkg")
            } catch (e: JSONException) {
                Log.e("JSON_ERROR", "Error parsing JSON: $e")
            }


            println("android.template: $textTemplate ")
            println("android.text conditions: $textTemplate")
            println("android.title conditions: $titleFinal")
            println("package name : $pkg")


            val instance = LogDatabase.getInstance(this)
            val dataDao = instance.logDao()


            val users: List<LogData> = dataDao.getAll()
            println(users)

            // Insert data on a background thread
            dataDao.insertAll(
                LogData(
                    System.currentTimeMillis(),
                    whenTime.toString(),
                    titleFinal,
                    textTemplate,
                    pkg
                )
            )


            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                DataSavingWorker::class.java, 15, TimeUnit.MINUTES
            ).build()
            WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)


//            val workManager = WorkManager.getInstance(applicationContext)
//
//            val workRequest = PeriodicWorkRequestBuilder<DataSavingWorker>(15, TimeUnit.MINUTES)
//                .build()
//
//            workManager.enqueue(workRequest)

            Log.d("ROOMDATA", "${dataDao.getAll().toString()}")
            stopForeground(Service.STOP_FOREGROUND_REMOVE)
            Log.d("INVOCATION", "Foreground Service Stopped")

        }


    }
}
