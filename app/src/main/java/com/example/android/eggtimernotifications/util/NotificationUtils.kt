/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.eggtimernotifications.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.android.eggtimernotifications.MainActivity
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.receiver.SnoozeReceiver

// Notification ID.
private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0
private const val FLAGS = 0

// TODO: Step 1.1 extension function to send messages (GIVEN)
/**
 * Builds and delivers the notification.
 *
 * @param messageBody notification text
 * @param applicationContext application's context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val intentToOpenActivity = Intent(applicationContext, MainActivity::class.java)

    val pendingIntentToOpenActivity = PendingIntent.getActivity(
        applicationContext,
        REQUEST_CODE,
        intentToOpenActivity,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntentCompat.FLAG_IMMUTABLE
    )

    val eggImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.cooked_egg
    )

    val style = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)

    val intentToSnooze = Intent(applicationContext, SnoozeReceiver::class.java)
    val pendingIntentToSnooze = PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        intentToSnooze,
        PendingIntent.FLAG_ONE_SHOT or PendingIntentCompat.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.egg_notification_channel_id)
    )


    // TODO: Step 1.8 use the new 'breakfast' notification channel

    builder
        .setSmallIcon(R.drawable.cooked_egg)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setLargeIcon(eggImage)
        .setStyle(style)
        .addAction(
            R.drawable.egg_icon,
            applicationContext.getString(R.string.snooze),
            pendingIntentToSnooze
        )
        .setContentIntent(pendingIntentToOpenActivity)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    // TODO: Step 2.1 add style to builder

    // TODO: Step 2.3 add snooze action

    // TODO: Step 2.5 set priority

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
