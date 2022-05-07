package com.ngangavictor.smsmycall.android

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import com.ngangavictor.smsmycall.Greeting
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephonyManager.registerTelephonyCallback(
                ContextCompat.getMainExecutor(this),
                object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                    override fun onCallStateChanged(state: Int) {
                        if (state == TelephonyManager.CALL_STATE_RINGING) {
                            Toast.makeText(this@MainActivity, "Phone Is Ringing", Toast.LENGTH_LONG)
                                .show();

                        }
                        // If incoming call received
                        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                            Toast.makeText(
                                this@MainActivity,
                                "Phone is Currently in A call",
                                Toast.LENGTH_LONG
                            ).show();
                        }


                        if (state == TelephonyManager.CALL_STATE_IDLE) {
                            Toast.makeText(
                                this@MainActivity,
                                "phone is neither ringing nor in a call",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                })
        } else {
            telephonyManager.listen(object : PhoneStateListener() {
                override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                    if (state == TelephonyManager.CALL_STATE_RINGING) {
                        Toast.makeText(this@MainActivity,"Phone Is Ringing"+phoneNumber, Toast.LENGTH_LONG).show();

                    }
                    // If incoming call received
                    if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                        Toast.makeText(
                            this@MainActivity,
                            "Phone is Currently in A call",
                            Toast.LENGTH_LONG
                        ).show();
                    }


                    if (state == TelephonyManager.CALL_STATE_IDLE) {
                        Toast.makeText(
                            this@MainActivity,
                            "phone is neither ringing nor in a call",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE)
        }

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { isGranted ->
                var count = 0
                for (i in isGranted) {
                    i.value
                    count++
                }
                if (count == 0) {
                    finish()
                }
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CALL_LOG
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CALL_LOG
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ANSWER_PHONE_CALLS
            ) == PackageManager.PERMISSION_GRANTED
            ) {

            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ANSWER_PHONE_CALLS,
                            Manifest.permission.MODIFY_PHONE_STATE
                        )
                    )
                }
            }
        }
    }
}
