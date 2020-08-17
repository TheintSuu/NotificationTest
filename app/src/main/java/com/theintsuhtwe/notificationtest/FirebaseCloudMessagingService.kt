package com.theintsuhtwe.notificationtest

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseCloudMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {

        //sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }
}