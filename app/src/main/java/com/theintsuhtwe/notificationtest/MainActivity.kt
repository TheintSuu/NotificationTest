package com.theintsuhtwe.notificationtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CHANNel_ID = "NOTIFICATION_CHANNEL"
    val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val notificationId = 1998
        val notificationId02 = 1999
        val notificationId03 = 2000
        val notificationId04 = 2001
        val notificationId05 = 2002
        val notificationId06 = 2003

        val textTitle = "Hello Friend"
        val textContent = "I am a New Notification"


        val builder = NotificationCompat.Builder(this, CHANNel_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        btnCreate.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId, builder.build())
            }

        }

        //Notification with Intent
        val intent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(
            this, 0, intent, 0
        )

        val builder2 = NotificationCompat.Builder(this, CHANNel_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        btnIntent.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId02, builder2.build())
            }
        }


        //Notification with Snooze Button
        val snoozeIntent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra(EXTRA_NOTIFICATION_ID, 0)
            }

        val snoozePendingIntent : PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
        val builder3 = NotificationCompat.Builder(this, CHANNel_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My Notification")
            .setContentText("Hello Action")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //.setContentIntent(pendingIntent)
            .addAction(android.R.drawable.star_off,"Snooze", snoozePendingIntent)


        btnAction.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId03, builder3.build())
            }
        }

        //Progress bar with notification
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 10

        val builder4 = NotificationCompat.Builder(this, CHANNel_ID).apply {
            setContentTitle("Picture Downolad")
            setContentText("Download in Progress")
            setSmallIcon(android.R.drawable.stat_sys_download)

        }

        NotificationManagerCompat.from(this).apply {
            builder4.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            builder.setContentText("Download Complete")
                .setProgress(0, 0, false)
            btnProgress.setOnClickListener {

                notify(notificationId04, builder4.build())

            }
        }


        //notification with image


        val drawable : Drawable ?= ContextCompat.getDrawable(this, R.drawable.day)
        val bitmap : Bitmap?=  drawableToBitmapExtension(drawable!!)

        val builder5 = NotificationCompat.Builder(this, CHANNel_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("imageTitle")
            .setContentText("imageDescription")
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(bitmap))

        btnNotiWithImage.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId05, builder5.build())
            }
        }


        //Notification with custom
        val notificationLayout = RemoteViews(packageName, R.layout.custom_noti_layout)


        val customNotification = NotificationCompat.Builder(this, CHANNel_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
          //  .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)


        btnCustom.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId06, customNotification.build())
            }
        }


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Token", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg ="token :  $token"
                Log.i("Token ", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "PADC Myanmar"
            val descriptionText = "PADC Learning Portal"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNel_ID, name, importance).apply {
                description  = descriptionText
            }

            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun progressNotificationChannel(){

    }

}