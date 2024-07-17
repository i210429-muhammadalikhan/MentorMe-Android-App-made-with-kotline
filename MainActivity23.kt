package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity23 : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var appointmentCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main23)

        database = FirebaseDatabase.getInstance().reference


        val appointmentsRef = database.child("appointments")
        appointmentsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appointmentSnapshot in dataSnapshot.children) {

                    val appointmentDate = appointmentSnapshot.key.toString()


                    val appointmentTime = appointmentSnapshot.value.toString()


                    displayAppointment(appointmentDate, appointmentTime)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        val backwardArrow = findViewById<TextView>(R.id.backwardArrow)
        backwardArrow.setOnClickListener {
            val intent = Intent(this@MainActivity23, MainActivity21::class.java)
            startActivity(intent)
        }
    }

    private fun displayAppointment(date: String, time: String) {

        appointmentCount++


        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewTime = findViewById<TextView>(R.id.textViewTime)

        val textViewDate2 = findViewById<TextView>(R.id.textViewDate2)
        val textViewTime2 = findViewById<TextView>(R.id.textViewTime2)

        val textViewDate3 = findViewById<TextView>(R.id.textViewDate3)
        val textViewTime3 = findViewById<TextView>(R.id.textViewTime3)

        when (appointmentCount) {
            1 -> {
                textViewDate.text = date
                textViewTime.text = time
            }
            2 -> {
                textViewDate2.text = date
                textViewTime2.text = time
            }
            3 -> {
                textViewDate3.text = date
                textViewTime3.text = time
            }
            else -> {

            }
        }
    }
}
