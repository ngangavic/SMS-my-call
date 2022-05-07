package com.ngangavictor.smsmycall.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class ServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.extras != null) {
            val number =
                intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER, "-1")

            if (number != "-1" || number != ("")) {
                Toast.makeText(context, number, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "No number", Toast.LENGTH_LONG).show()
            }
        }
    }
}