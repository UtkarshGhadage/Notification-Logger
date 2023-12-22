package com.example.notificationlogger

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager
import com.example.notificationlogger.Retrofit.RetrofitBuilder
import com.example.notificationlogger.Retrofit.RetrofitInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isNotificationPermissionGranted()) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        isNotificationPermissionGranted()
        startNotificationLoggerService()

        val dataApi = RetrofitBuilder.getInstance().create(RetrofitInterface::class.java)
        // launching a new coroutine
        GlobalScope.launch{
            var result = dataApi.getData()
            if (result != null)
            // Checking the results
                Log.d("LOGGGGG: ", result.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val workManager = WorkManager.getInstance(NotificationLoggerService())
        workManager.cancelAllWork()
    }

    fun startNotificationLoggerService() {
        val intent = Intent(this, NotificationLoggerService::class.java)
        startService(intent)
    }

    private fun isNotificationPermissionGranted(): Boolean {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.isNotificationListenerAccessGranted(ComponentName(
            application,
            NotificationLoggerService::class.java
        ))
    }
}
