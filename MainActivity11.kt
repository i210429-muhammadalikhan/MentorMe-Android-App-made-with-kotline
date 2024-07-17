package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity11 : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var experienceEditText: EditText
    private var rating: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main11)


        database = FirebaseDatabase.getInstance().reference


        experienceEditText = findViewById(R.id.experienceEditText)
        val firstStar = findViewById<ImageView>(R.id.firststar)
        val secondStar = findViewById<ImageView>(R.id.secondstar)
        val thirdStar = findViewById<ImageView>(R.id.thirdstar)
        val fourthStar = findViewById<ImageView>(R.id.fourthstar)
        val fifthStar = findViewById<ImageView>(R.id.fifthstar)

        val submitButton = findViewById<Button>(R.id.submitButton)
        val backwardArrow = findViewById<TextView>(R.id.backwardArrow)


        firstStar.setOnClickListener {
            rating = 1

        }

        secondStar.setOnClickListener {
            rating = 2

        }

        thirdStar.setOnClickListener {
            rating = 3

        }


        fourthStar.setOnClickListener {
            rating = 4

        }


        fifthStar.setOnClickListener {
            rating = 5

        }


        submitButton.setOnClickListener {
            val experience = experienceEditText.text.toString().trim()

            if (experience.isNotEmpty()) {
                saveFeedbackToFirebase(experience, rating)
            } else {
                Toast.makeText(this, "Please enter your experience", Toast.LENGTH_SHORT).show()
            }
        }

        backwardArrow.setOnClickListener {
            val intent = Intent(this, MainActivity10::class.java)
            startActivity(intent)
        }
    }

    private fun saveFeedbackToFirebase(experience: String, rating: Int) {
        val feedbackKey = database.child("feedback").push().key ?: ""

        val feedbackData = mapOf(
            "experience" to experience,
            "rating" to rating
        )

        database.child("feedback").child(feedbackKey).setValue(feedbackData)
            .addOnSuccessListener {
                Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show()
                experienceEditText.text.clear()
                this.rating = 0
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to submit feedback: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
