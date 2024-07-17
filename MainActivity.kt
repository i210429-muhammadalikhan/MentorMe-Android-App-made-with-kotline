package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alikhan.i210429.MainActivity2
import com.alikhan.i210429.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
                val loginIntent = Intent(this, MainActivity2::class.java)
                startActivity(loginIntent)
                finish()

        }, 5000)
    }
}
