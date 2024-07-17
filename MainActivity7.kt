package com.alikhan.i210429

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity7 : AppCompatActivity() {

    lateinit var topMentorName1: TextView
    lateinit var topMentorDesignation1: TextView
    lateinit var topMentorStatus1: TextView
    lateinit var topMentorPrice1: TextView


    lateinit var topMentorName2: TextView
    lateinit var topMentorDesignation2: TextView
    lateinit var topMentorStatus2: TextView
    lateinit var topMentorPrice2: TextView

    lateinit var topMentorName3: TextView
    lateinit var topMentorDesignation3: TextView
    lateinit var topMentorStatus3: TextView
    lateinit var topMentorPrice3: TextView


    lateinit var topMentorNamebelow1: TextView
    lateinit var topMentorDesignationbelow1: TextView
    lateinit var topMentorStatusbelow1: TextView
    lateinit var topMentorPricebelow1: TextView

    lateinit var topMentorNamebelow2: TextView
    lateinit var topMentorDesignationbelow2: TextView
    lateinit var topMentorStatusbelow2: TextView
    lateinit var topMentorPricebelow2: TextView

    lateinit var topMentorNamebelow3: TextView
    lateinit var topMentorDesignationbelow3: TextView
    lateinit var topMentorStatusbelow3: TextView
    lateinit var topMentorPricebelow3: TextView


    lateinit var mentorsRef: DatabaseReference
    lateinit var mentorListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        topMentorName1 = findViewById<TextView>(R.id.topMentorName)
        topMentorDesignation1 = findViewById<TextView>(R.id.topMentorDesignation)
        topMentorStatus1 = findViewById<TextView>(R.id.topMentorStatus)
        topMentorPrice1 = findViewById<TextView>(R.id.topMentorPrice)

        topMentorName2 = findViewById<TextView>(R.id.topMentorName2)
        topMentorDesignation2 = findViewById<TextView>(R.id.topMentorDesignation2)
        topMentorStatus2 = findViewById<TextView>(R.id.topMentorStatus2)
        topMentorPrice2 = findViewById<TextView>(R.id.topMentorPrice2)

        topMentorName3 = findViewById<TextView>(R.id.topMentorName3)
        topMentorDesignation3 = findViewById<TextView>(R.id.topMentorDesignation3)
        topMentorStatus3 = findViewById<TextView>(R.id.topMentorStatus3)
        topMentorPrice3 = findViewById<TextView>(R.id.topMentorPrice3)


        topMentorNamebelow1 = findViewById<TextView>(R.id.topMentorNamebelow1)
        topMentorDesignationbelow1 = findViewById<TextView>(R.id.topMentorDesignationbelow1)
        topMentorStatusbelow1 = findViewById<TextView>(R.id.topMentorStatusbelow1)
        topMentorPricebelow1 = findViewById<TextView>(R.id.topMentorPricebelow1)

        topMentorNamebelow2 = findViewById<TextView>(R.id.topMentorNamebelow2)
        topMentorDesignationbelow2 = findViewById<TextView>(R.id.topMentorDesignationbelow2)
        topMentorStatusbelow2 = findViewById<TextView>(R.id.topMentorStatusbelow2)
        topMentorPricebelow2 = findViewById<TextView>(R.id.topMentorPricebelow2)

        topMentorNamebelow3 = findViewById<TextView>(R.id.topMentorNamebelow3)
        topMentorDesignationbelow3 = findViewById<TextView>(R.id.topMentorDesignationbelow3)
        topMentorStatusbelow3 = findViewById<TextView>(R.id.topMentorStatusbelow3)
        topMentorPricebelow3 = findViewById<TextView>(R.id.topMentorPricebelow3)


        mentorsRef = FirebaseDatabase.getInstance().getReference("mentors")
        mentorListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mentors = mutableListOf<newmentor>()
                for (childSnapshot in snapshot.children) {
                    val mentor = childSnapshot.getValue(newmentor::class.java)
                    mentor?.let { mentors.add(it) }
                }
                if (mentors.size >= 3) {
                    val latestMentor1 = mentors[mentors.size - 1]
                    val latestMentor2 = mentors[mentors.size - 2]
                    val latestMentor3 = mentors[mentors.size - 3]

                    setMentorInformation(topMentorName1, topMentorDesignation1, topMentorStatus1, topMentorPrice1, latestMentor1)
                    setMentorInformation(topMentorName2, topMentorDesignation2, topMentorStatus2, topMentorPrice2,latestMentor2)
                    setMentorInformation(topMentorName3, topMentorDesignation3, topMentorStatus3, topMentorPrice3,latestMentor3)

                    setMentorInformation(topMentorNamebelow1, topMentorDesignationbelow1, topMentorStatusbelow1, topMentorPricebelow1, latestMentor3)
                    setMentorInformation(topMentorNamebelow2, topMentorDesignationbelow2, topMentorStatusbelow2, topMentorPricebelow2, latestMentor1)
                    setMentorInformation(topMentorNamebelow3, topMentorDesignationbelow3, topMentorStatusbelow3, topMentorPricebelow3, latestMentor2)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        mentorsRef.addValueEventListener(mentorListener)

        val dotTextView = findViewById<TextView>(R.id.dotText)
        dotTextView.setOnClickListener {
            val intent = Intent(this, MainActivity24::class.java)
            startActivity(intent)
        }

        val searchImage = findViewById<ImageView>(R.id.searchImage)
        searchImage.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

        val chatImage = findViewById<ImageView>(R.id.chatoptImage)
        chatImage.setOnClickListener {
            val intent = Intent(this, MainActivity14::class.java)
            startActivity(intent)
        }

        val profileImage = findViewById<ImageView>(R.id.profileoptImage)
        profileImage.setOnClickListener {
            val intent = Intent(this, MainActivity21::class.java)
            startActivity(intent)
        }

        val plus = findViewById<ImageView>(R.id.plus)
        plus.setOnClickListener {
            val intent = Intent(this, MainActivity12::class.java)
            startActivity(intent)
        }
    }

    private fun setMentorInformation(
        mentorName: TextView,
        mentorDesignation: TextView,
        mentorStatus: TextView,
        mentorPrice: TextView,
        mentor: newmentor
    ) {
        mentorName.text = mentor.name
        mentorDesignation.text = " ${mentor.designation}                   ❤\uFE0F"
        mentorStatus.text = "\uD83D\uDFE2 ${mentor.status}"
        mentorPrice.text = mentor.price

        mentorName.setOnClickListener {
            val intent = Intent(this@MainActivity7, MainActivity21::class.java)
            intent.putExtra("mentorName", mentor.name)
            intent.putExtra("mentorDesignation", mentor.designation)
            intent.putExtra("mentorStatus", mentor.status)
            intent.putExtra("mentorPrice", mentor.price)

            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mentorsRef.removeEventListener(mentorListener)
    }
}
