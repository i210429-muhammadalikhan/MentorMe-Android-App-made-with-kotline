package com.alikhan.i210429

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity16 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        val msgpic15 = findViewById<ImageView>(R.id.msgpic15)

        msgpic15.setOnClickListener {
            val intent = Intent(this, MainActivity17::class.java)
            startActivity(intent)
        }

        val chat15pic = findViewById<ImageView>(R.id.chat15pic)

        chat15pic.setOnClickListener {
            val intent = Intent(this, MainActivity20::class.java)
            startActivity(intent)
        }

        val backwardArrow = findViewById<TextView>(R.id.backwardArrow)

        backwardArrow.setOnClickListener {
            val intent = Intent(this, MainActivity10::class.java)
            startActivity(intent)
        }
    }
}