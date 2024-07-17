package com.alikhan.i210429

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alikhan.i210429.databinding.ActivityMain20Binding
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig

class MainActivity20 : AppCompatActivity() {
    private val appId = "d7f285b86c1044ae819b60f5c6eca493"
    private val channelName = "smdA2"
    private var token: String? = "007eJxTYLCelFMdVPdzxvNrz6ujd/9wcTMpUtipfSaseEGn8L4NuiUKDCnmaUYWpkkWZsmGBiYmiakWhpZJZgZppslmqcmJJpbGGyb/Sm0IZGTY8dWKkZEBAkF8Vobi3BRHIwYGANUkIU4="
    private val uid = 0
    private var isJoined = false
    private var agoraEngine: RtcEngine? = null
    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf(
        android.Manifest.permission.RECORD_AUDIO
    )

    private lateinit var binding: ActivityMain20Binding

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            showMessage("Remote user offline $uid $reason")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain20Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        setupAudioSDKEngine()

        // Set OnClickListener for micMuteImage
        binding.micMuteImage.setOnClickListener {
            if (!isJoined) {
                showMessage("Call has been joined")
            } else {
                showMessage("Call joined")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        agoraEngine?.leaveChannel()
        RtcEngine.destroy()
        agoraEngine = null
    }

    private fun checkSelfPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED
    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupAudioSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            agoraEngine?.enableAudio()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    fun joinChannel(view: View) {
        if (checkSelfPermission()) {
            agoraEngine?.joinChannel(token, channelName, null, uid)
        } else {
            Toast.makeText(applicationContext, "Permissions for audio recording not granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun leaveChannel(view: View) {
        if (!isJoined) {
            showMessage("Join a channel first")
        } else {
            agoraEngine?.leaveChannel()
            showMessage("You left the channel")
            isJoined = false
        }
    }
}

