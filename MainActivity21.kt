package com.alikhan.i210429

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.IOException
import android.util.TypedValue



class MainActivity21 : AppCompatActivity() {

    private lateinit var mentorNameTextView: TextView
    private lateinit var mentorDesignationTextView: TextView
    private lateinit var mentorStatusTextView: TextView
    private lateinit var mentorPriceTextView: TextView

    private lateinit var mentorNameTextView2: TextView
    private lateinit var mentorDesignationTextView2: TextView
    private lateinit var mentorStatusTextView2: TextView
    private lateinit var mentorPriceTextView2: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userNameTextView: TextView
    private lateinit var userCityTextView: TextView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var newProfilePicImageView: ImageView
    private lateinit var rightPairLayout: LinearLayout

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main21)

        mentorNameTextView = findViewById(R.id.mentorNameTextView1)
        mentorDesignationTextView = findViewById(R.id.mentorDesignationTextView1)
        mentorStatusTextView = findViewById(R.id.mentorStatusTextView1)
        mentorPriceTextView = findViewById(R.id.mentorPriceTextView1)

        mentorNameTextView2 = findViewById(R.id.mentorNameTextView2)
        mentorDesignationTextView2 = findViewById(R.id.mentorDesignationTextView2)
        mentorStatusTextView2 = findViewById(R.id.mentorStatusTextView2)
        mentorPriceTextView2 = findViewById(R.id.mentorPriceTextView2)


        sharedPreferences = getSharedPreferences("MentorPrefs", MODE_PRIVATE)
        userNameTextView = findViewById(R.id.userNameTextView)
        userCityTextView = findViewById(R.id.userCityTextView)
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        newProfilePicImageView = findViewById(R.id.newprofilepic)
        rightPairLayout = findViewById(R.id.rightPairLayout)

        findViewById<View>(R.id.penciliconprofile).setOnClickListener {
            openGallery()
        }


        val extras = intent.extras
        if (extras != null) {
            val mentorName = extras.getString("mentorName", "")
            val mentorDesignation = extras.getString("mentorDesignation", "")
            val mentorStatus = extras.getString("mentorStatus", "")
            val mentorPrice = extras.getString("mentorPrice", "")

            mentorNameTextView.text = mentorName
            mentorDesignationTextView.text = mentorDesignation
            mentorStatusTextView.text = mentorStatus
            mentorPriceTextView.text = mentorPrice

            mentorNameTextView2.text = mentorName
            mentorDesignationTextView2.text = mentorDesignation
            mentorStatusTextView2.text = mentorStatus
            mentorPriceTextView2.text = mentorPrice


            saveMentorInfo(mentorName, mentorDesignation, mentorStatus, mentorPrice)
        } else {
            val storedMentorName = sharedPreferences.getString("mentorName", "")
            val storedMentorDesignation = sharedPreferences.getString("mentorDesignation", "")
            val storedMentorStatus = sharedPreferences.getString("mentorStatus", "")
            val storedMentorPrice = sharedPreferences.getString("mentorPrice", "")


            mentorNameTextView.text = storedMentorName
            mentorDesignationTextView.text = " ${storedMentorDesignation}           ❤\uFE0F"
            mentorStatusTextView.text =  "\uD83D\uDFE2 ${storedMentorStatus}"
            mentorPriceTextView.text = storedMentorPrice

            mentorNameTextView2.text = storedMentorName
            mentorDesignationTextView2.text = " ${storedMentorDesignation}           ❤\uFE0F"
            mentorStatusTextView2.text = "\uD83D\uDFE2 ${storedMentorStatus}"
            mentorPriceTextView2.text = storedMentorPrice

        }


        val profile21 = findViewById<ImageView>(R.id.profile21)
        profile21.setOnClickListener {
            val intent = Intent(this, MainActivity22::class.java)
            startActivity(intent)
        }

        fetchUserDataFromFirebase()
        displayFeedbacksFromFirebase()
        displayMentors()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                newProfilePicImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchUserDataFromFirebase() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            val userRef = databaseReference.child(uid)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userName = snapshot.child("name").getValue(String::class.java)
                        val userCity = snapshot.child("city").getValue(String::class.java)

                        userNameTextView.text = "$userName"
                        userCityTextView.text = " \uD83D\uDCCD $userCity"
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun onBookedSessionsButtonClick(view: View) {
        val intent = Intent(this, MainActivity23::class.java)
        startActivity(intent)
    }


    private fun displayFeedbacksFromFirebase() {
        val feedbacksRef = FirebaseDatabase.getInstance().getReference("feedback")

        feedbacksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val leftPairLayout = findViewById<LinearLayout>(R.id.leftPairLayout)
                val rightPairLayout = findViewById<LinearLayout>(R.id.rightPairLayout)
                leftPairLayout.removeAllViews()
                rightPairLayout.removeAllViews()

                var isFirstPair = true

                for (feedbackSnapshot in snapshot.children) {
                    val experience = feedbackSnapshot.child("experience").getValue(String::class.java)
                    val rating = feedbackSnapshot.child("rating").getValue(Int::class.java)

                    val pairLayout = LinearLayout(this@MainActivity21)
                    pairLayout.orientation = LinearLayout.VERTICAL



                    val ratingTextView = TextView(this@MainActivity21)
                    ratingTextView.text = rating.toString()
                    ratingTextView.text = "Rating: $rating"
                    ratingTextView.setTextColor(Color.BLACK)
                    ratingTextView.setPadding(20, 20, 0, 0)
                    ratingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.toFloat())
                    ratingTextView.setTypeface(null, Typeface.BOLD)




                    val experienceTextView = TextView(this@MainActivity21)
                    experienceTextView.text = experience
                    experienceTextView.setTextColor(Color.BLACK)
                    experienceTextView.setPadding(20, 20, 0, 0)
                    experienceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.toFloat())
                    experienceTextView.setTypeface(null, Typeface.BOLD)


                    pairLayout.addView(ratingTextView)
                    pairLayout.addView(experienceTextView)

                    if (isFirstPair) {
                        leftPairLayout.addView(pairLayout)
                        isFirstPair = false
                    } else {
                        rightPairLayout.addView(pairLayout)
                        isFirstPair = true
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read feedbacks", error.toException())
            }
        })
    }



    private fun displayMentors() {

        val mentorList = mutableListOf("Mentor A", "Mentor B", "Mentor C")

        for (mentorName in mentorList) {
            val mentorTextView = TextView(this)
            mentorTextView.text = mentorName
            mentorTextView.setTextColor(Color.BLACK)
            rightPairLayout.addView(mentorTextView)
        }
    }

    private fun saveMentorInfo(mentorName: String, mentorDesignation: String, mentorStatus: String, mentorPrice: String) {
        val editor = sharedPreferences.edit()
        editor.putString("mentorName", mentorName)
        editor.putString("mentorDesignation", mentorDesignation)
        editor.putString("mentorStatus", mentorStatus)
        editor.putString("mentorPrice", mentorPrice)

        editor.apply()
    }
}
