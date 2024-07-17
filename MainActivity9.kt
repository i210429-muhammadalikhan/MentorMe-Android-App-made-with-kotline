package com.alikhan.i210429

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView

class MainActivity9 : AppCompatActivity() {

    companion object {
        const val AVAILABLE_OPTION = 1
        const val NOT_AVAILABLE_OPTION = 2
        const val LIKED_OPTION = 3
        const val NOT_LIKED_OPTION = 4
    }

    private lateinit var sampleOneImageView: ImageView
    private lateinit var sampleTwoImageView: ImageView
    private lateinit var sampleThreeImageView: ImageView
    private lateinit var sampleFourImageView: ImageView
    private lateinit var sampleFiveImageView: ImageView

    private lateinit var bs9: ImageView
    private lateinit var bss9: ImageView
    private lateinit var bsss9: ImageView
    private lateinit var bssss9: ImageView
    private lateinit var bsssss9: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)

        sampleOneImageView = findViewById(R.id.sampleone9)
        sampleTwoImageView = findViewById(R.id.sampletwo9)
        sampleThreeImageView = findViewById(R.id.samplethree9)
        sampleFourImageView = findViewById(R.id.samplefour9)
        sampleFiveImageView = findViewById(R.id.samplefive9)

        bs9 = findViewById(R.id.bs9)
        bss9 = findViewById(R.id.bss9)
        bsss9 = findViewById(R.id.bsss9)
        bssss9 = findViewById(R.id.bssss9)
        bsssss9 = findViewById(R.id.bsssss9)


        val plus = findViewById<ImageView>(R.id.plus)
        val filterText = findViewById<TextView>(R.id.filterText)

        plus.setOnClickListener {
            val intent = Intent(this, MainActivity12::class.java)
            startActivity(intent)
        }

        filterText.setOnClickListener { view ->
            showFilterMenu(view)
        }

        val johnnavigate = findViewById<ImageView>(R.id.sampleone9)

        johnnavigate.setOnClickListener {
            val intent = Intent(this, MainActivity10::class.java)
            startActivity(intent)
        }

        val backwardArrow = findViewById<TextView>(R.id.backwardArrow)

        backwardArrow.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }
    }

    private fun showFilterMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        popupMenu.menu.add(0, AVAILABLE_OPTION, 0, "Available")
        popupMenu.menu.add(0, NOT_AVAILABLE_OPTION, 0, "Not Available")
        popupMenu.menu.add(0, LIKED_OPTION, 0, "Liked")
        popupMenu.menu.add(0, NOT_LIKED_OPTION, 0, "Not Liked")

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->

            when (item.itemId) {
                AVAILABLE_OPTION -> {

                    sampleOneImageView.visibility = View.VISIBLE
                    sampleTwoImageView.visibility = View.GONE
                    sampleThreeImageView.visibility = View.GONE
                    sampleFourImageView.visibility = View.VISIBLE
                    sampleFiveImageView.visibility = View.VISIBLE

                    bs9.visibility = View.VISIBLE
                    bss9.visibility = View.GONE
                    bsss9.visibility = View.GONE
                    bssss9.visibility = View.VISIBLE
                    bsssss9.visibility = View.VISIBLE


                    true
                }
                NOT_AVAILABLE_OPTION -> {

                    sampleOneImageView.visibility = View.GONE
                    sampleTwoImageView.visibility = View.VISIBLE
                    sampleThreeImageView.visibility = View.VISIBLE
                    sampleFourImageView.visibility = View.GONE
                    sampleFiveImageView.visibility = View.GONE

                    bs9.visibility = View.GONE
                    bss9.visibility = View.VISIBLE
                    bsss9.visibility = View.VISIBLE
                    bssss9.visibility = View.GONE
                    bsssss9.visibility = View.GONE

                    true
                }
                LIKED_OPTION -> {

                    sampleOneImageView.visibility = View.VISIBLE
                    sampleTwoImageView.visibility = View.GONE
                    sampleThreeImageView.visibility = View.GONE
                    sampleFourImageView.visibility = View.VISIBLE
                    sampleFiveImageView.visibility = View.VISIBLE

                    bs9.visibility = View.VISIBLE
                    bss9.visibility = View.GONE
                    bsss9.visibility = View.GONE
                    bssss9.visibility = View.VISIBLE
                    bsssss9.visibility = View.VISIBLE

                    true
                }
                NOT_LIKED_OPTION -> {

                    sampleOneImageView.visibility = View.GONE
                    sampleTwoImageView.visibility = View.VISIBLE
                    sampleThreeImageView.visibility = View.VISIBLE
                    sampleFourImageView.visibility = View.GONE
                    sampleFiveImageView.visibility = View.GONE

                    bs9.visibility = View.GONE
                    bss9.visibility = View.VISIBLE
                    bsss9.visibility = View.VISIBLE
                    bssss9.visibility = View.GONE
                    bsssss9.visibility = View.GONE

                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}
