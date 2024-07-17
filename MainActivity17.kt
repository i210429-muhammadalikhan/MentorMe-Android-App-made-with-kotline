package com.alikhan.i210429

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity17 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main17)

        val videoText = findViewById<TextView>(R.id.videoText)

        videoText.setOnClickListener {
            val intent = Intent(this, MainActivity18::class.java)
            startActivity(intent)
        }

        val cross = findViewById<ImageView>(R.id.cross)

        cross.setOnClickListener {
            val intent = Intent(this, MainActivity16::class.java)
            startActivity(intent)
        }
    }
}