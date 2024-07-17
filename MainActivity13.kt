package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.alikhan.i210429.MainActivity23
import com.alikhan.i210429.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity13 : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main13)

        database = FirebaseDatabase.getInstance().reference

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            selectedDate = "$year-${month + 1}-$dayOfMonth"
        }

        val tenImageView = findViewById<ImageView>(R.id.ten)
        tenImageView.setOnClickListener {
            saveAppointmentTime("10:00 AM")
        }

        val elevenImageView = findViewById<ImageView>(R.id.eleven)
        elevenImageView.setOnClickListener {
            saveAppointmentTime("11:00 AM")
        }

        val twelveImageView = findViewById<ImageView>(R.id.twelve)
        twelveImageView.setOnClickListener {
            saveAppointmentTime("12:00 PM")
        }
    }

    private fun saveAppointmentTime(time: String) {

        if (::selectedDate.isInitialized) {

            val appointmentRef = database.child("appointments").child(selectedDate)
            appointmentRef.setValue(time)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        } else {

        }
    }

    fun onBookAppointmentButtonClick(view: View) {
        val intent = Intent(this, MainActivity23::class.java)
        startActivity(intent)
    }
}
