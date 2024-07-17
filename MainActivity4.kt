package com.alikhan.i210429

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity4 : AppCompatActivity() {

    private lateinit var screenshotReceiver: ScreenshotReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val verifyButton = findViewById<Button>(R.id.verifyButton)

        verifyButton.setOnClickListener {
            Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }

        val backwardArrow = findViewById<TextView>(R.id.backwardArrow)

        backwardArrow.setOnClickListener {

            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }


        screenshotReceiver = ScreenshotReceiver()
        registerReceiver(screenshotReceiver, IntentFilter("android.intent.action.SCREENSHOT"))
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(screenshotReceiver)
    }


    inner class ScreenshotReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            Toast.makeText(context, "Screenshot has been taken", Toast.LENGTH_SHORT).show()
        }
    }
}
