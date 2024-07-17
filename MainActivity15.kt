package com.alikhan.i210429

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class MainActivity15 : AppCompatActivity() {
    private val PICK_MEDIA_REQUEST = 1
    private val PICK_IMAGE_REQUEST = 1
    private val CAMERA_REQUEST = 1888

    private lateinit var storageReference: StorageReference
    private lateinit var database: FirebaseDatabase
    private lateinit var messageContainer: LinearLayout
    private lateinit var sharedPreferences: SharedPreferences

    private var messageTextViewToDelete: TextView? = null
    private var messageEditTextToEdit: EditText? = null

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var outputFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main15)

        storageReference = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val call15 = findViewById<ImageView>(R.id.call15)
        val backwardArrow = findViewById<TextView>(R.id.backwardArrow)
        val videocall15 = findViewById<ImageView>(R.id.videocall15)
        val clipiconText = findViewById<TextView>(R.id.clipiconText)
        val cameraiconText = findViewById<TextView>(R.id.cameraiconText)
        val voicenoteiconText = findViewById<TextView>(R.id.voicenoteiconText)
        val sendmessageiconText = findViewById<TextView>(R.id.sendmessageiconText)
        messageContainer = findViewById(R.id.messageContainer)

        call15.setOnClickListener {
            val intent = Intent(this, MainActivity20::class.java)
            startActivity(intent)
        }

        backwardArrow.setOnClickListener {
            val intent = Intent(this, MainActivity14::class.java)
            startActivity(intent)
        }

        videocall15.setOnClickListener {
            val intent = Intent(this, MainActivity19::class.java)
            startActivity(intent)
        }

        cameraiconText.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }

        clipiconText.setOnClickListener {
            openGallery()
        }

        voicenoteiconText.setOnClickListener {
            if (!isRecording) {
                startRecording()
            } else {
                stopRecording()
            }
        }

        sendmessageiconText.setOnClickListener {
            sendMessage()
        }

        registerForContextMenu(messageContainer)


        val messages = sharedPreferences.getStringSet(currentUser?.uid ?: "", HashSet()) ?: HashSet()
        for (message in messages) {
            addMessageToUI(message)
        }

        val reference = database.getReference("messages")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(String::class.java)
                if (message != null) {
                    addMessageToUI(message)
                    messages.add(message)
                    sharedPreferences.edit().putStringSet(currentUser?.uid ?: "", messages).apply()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity15, "Failed to load messages: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v == messageContainer) {
            menu?.add(0, v?.id ?: 0, 0, "Delete")
            menu?.add(0, v?.id ?: 0, 0, "Edit")
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Delete" -> {
                deleteMessage()
                true
            }
            "Edit" -> {
                editMessage()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*, image/*"
        startActivityForResult(intent, PICK_MEDIA_REQUEST)
    }

    private fun storeImageToFirebase(imageUri: Uri?) {
        if (imageUri != null) {
            val imageName = "image_" + System.currentTimeMillis() + ".jpg"
            val imageRef = storageReference.child("images/$imageName")

            imageRef.putFile(imageUri)
                .addOnSuccessListener {
                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data

            val imageView = findViewById<ImageView>(R.id.selectedImage)
            imageView.setImageURI(imageUri)


            storeImageToFirebase(imageUri)
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap

        }
    }

    private fun storeMediaToFirebase(mediaUri: Uri?) {
        if (mediaUri != null) {
            val mediaName = "media_" + System.currentTimeMillis() + if (mediaUri.toString().contains("video")) ".mp4" else ".jpg"
            val mediaRef = storageReference.child("media/$mediaName")

            mediaRef.putFile(mediaUri)
                .addOnSuccessListener {
                    Toast.makeText(this, "Media uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload media: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun sendMessage() {
        val msgEditText = findViewById<EditText>(R.id.msgEditText)
        val message = msgEditText.text.toString().trim()

        if (message.isNotEmpty()) {

            val reference = database.getReference("messages").push()
            reference.setValue(message)
                .addOnSuccessListener {

                    msgEditText.text.clear()
                    Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to send message: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRecording() {
        if (checkPermissions()) {
            try {

                val dir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                outputFile = "${dir?.absolutePath}/recording_${System.currentTimeMillis()}.3gp"
                mediaRecorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    setOutputFile(outputFile)
                    prepare()
                    start()
                }
                isRecording = true
                Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Failed to start recording: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            requestPermissions()
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false

            outputFile?.let { outputFile ->

                playRecordedVoice(outputFile)


                storeMediaToFirebase(Uri.fromFile(File(outputFile)))
                Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playRecordedVoice(filePath: String) {
        try {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare()
                start()
            }
            mediaPlayer.setOnCompletionListener {

                mediaPlayer.release()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Failed to play recorded voice: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording()
            } else {
                Toast.makeText(this, "Permission denied to record audio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMessage() {
        if (messageTextViewToDelete != null) {
            messageContainer.removeView(messageTextViewToDelete)
            messageTextViewToDelete = null

        }
    }

    private fun editMessage() {
        if (messageTextViewToDelete != null) {
            messageEditTextToEdit = EditText(this)
            messageEditTextToEdit?.setText(messageTextViewToDelete?.text)
            messageEditTextToEdit?.setTextColor(Color.BLACK)
            messageEditTextToEdit?.setPadding(16, 16, 16, 16)
            messageEditTextToEdit?.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val editedMessage = (v as EditText).text.toString()
                    messageTextViewToDelete?.text = editedMessage
                    messageContainer.removeView(v)
                    messageEditTextToEdit = null
                }
            }

            messageContainer.addView(messageEditTextToEdit)
        }
    }

    private fun addMessageToUI(message: String) {
        val textView = TextView(this)
        textView.text = message
        textView.textSize = 18f
        textView.setTextColor(Color.BLACK)
        textView.setPadding(16, 16, 16, 16)

        registerForContextMenu(textView)

        textView.setOnLongClickListener {
            messageTextViewToDelete = textView
            false
        }

        messageContainer.addView(textView)
    }
}
