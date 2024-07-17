package com.alikhan.i210429

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class MainActivity12 : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private lateinit var storageReference: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main12)

        val uploadButton = findViewById<Button>(R.id.uploadButton)

        storageReference = FirebaseStorage.getInstance()

        findViewById<ImageView>(R.id.uploadphotoImageView).setOnClickListener {
            openGallery()
        }

        uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            findViewById<ImageView>(R.id.uploadphotoImageView).setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        imageUri?.let { uri ->
            val imageName = UUID.randomUUID().toString()
            val storageRef = storageReference.reference.child("images/$imageName")
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        val name = findViewById<EditText>(R.id.nameEditText).text.toString()
                        val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
                        val status = findViewById<EditText>(R.id.statusEditText).text.toString()
                        val designation = findViewById<EditText>(R.id.designationEditText).text.toString()
                        val price = findViewById<EditText>(R.id.priceEditText).text.toString()

                        val mentor = Mentor(name, description, status, designation, price)

                        val databaseRef = FirebaseDatabase.getInstance().getReference("mentors")
                        databaseRef.push().setValue(mentor)
                            .addOnSuccessListener {
                                Toast.makeText(this@MainActivity12, "Upload successful", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this@MainActivity12, "Upload failed: ${it.message}", Toast.LENGTH_LONG).show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this@MainActivity12, "Upload failed: ${it.message}", Toast.LENGTH_LONG).show()
                }
        } ?: run {
            Toast.makeText(this@MainActivity12, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }
}

data class Mentor(val name: String, val description: String, val status: String, val designation: String, val price: String)
