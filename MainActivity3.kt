package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast

class MainActivity3 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        auth = FirebaseAuth.getInstance()

        val signUpButton = findViewById<Button>(R.id.SignUpButton)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val countryEditText = findViewById<EditText>(R.id.countryEditText)
        val cityEditText = findViewById<EditText>(R.id.cityEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val country = countryEditText.text.toString().trim()
            val city = cityEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()


            if (name.isEmpty() || email.isEmpty() || country.isEmpty() || city.isEmpty() || password.isEmpty()) {

                return@setOnClickListener
            }

            // Save user data to Firebase database
            val userRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser?.uid ?: "")
            val userData = newuser(name, email, country, city, password)
            userRef.setValue(userData)
                .addOnSuccessListener {

                    Toast.makeText(this, "Proceeding to Verify Mobile Phone", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity4::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->

                }
        }
    }
}
