package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity22 : AppCompatActivity() {

    private lateinit var backwardArrow: TextView
    private lateinit var randLogoImage: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactNumberEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var updateButton: Button

    private val user = FirebaseAuth.getInstance().currentUser
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main22)

        backwardArrow = findViewById(R.id.backwardArrow)
        randLogoImage = findViewById(R.id.randLogoImage)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        contactNumberEditText = findViewById(R.id.contactNumberEditText)
        countryEditText = findViewById(R.id.countryEditText)
        cityEditText = findViewById(R.id.cityEditText)
        updateButton = findViewById(R.id.updateButton)

        backwardArrow.setOnClickListener {
            val intent = Intent(this, MainActivity21::class.java)
            startActivity(intent)
        }

        updateButton.setOnClickListener {
            updateProfile()
        }

        // TODO: Fetch user data from Firebase and populate the EditText fields
    }

    private fun updateProfile() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val contactNumber = contactNumberEditText.text.toString()
        val country = countryEditText.text.toString()
        val city = cityEditText.text.toString()

        user?.let {

            val updatedUser = User(name, email, contactNumber, country, city)

            databaseReference.child(it.uid).setValue(updatedUser)
                .addOnSuccessListener {

                    val intent = Intent(this, MainActivity21::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {

                    Toast.makeText(
                        this@MainActivity22,
                        "Failed to update profile. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
