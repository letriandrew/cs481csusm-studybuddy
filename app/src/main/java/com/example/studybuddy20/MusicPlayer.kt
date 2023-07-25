package com.example.studybuddy20

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MusicPlayer : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private val mp3Files = arrayOf(R.raw.lofi, R.raw.jazz)
    private var selectedFile: Int? = null
    private var playbackPos = 0
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_player)
        val mp3Spinner: Spinner = findViewById(R.id.mp3_spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.mp3_files,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mp3Spinner.adapter = adapter
        }

        mp3Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("DiscouragedApi")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedFile = resources.getIdentifier(mp3Files[position].toString(), "raw", packageName)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //does nothing
            }

        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigation.setOnItemSelectedListener { item ->
            onNavigationItemSelected(item)
        }
    }


    fun play(view: View){
        if(!::mediaPlayer.isInitialized){
            mediaPlayer = MediaPlayer.create(this, selectedFile!!)
            mediaPlayer.start()
        }
        else {
            try {
                mediaPlayer.seekTo(playbackPos)
                mediaPlayer.start()
            } catch (e: IllegalStateException) {

                mediaPlayer = MediaPlayer.create(this, selectedFile!!)
                mediaPlayer.start()
            }
        }


    }
    fun pause(view: View){
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying){
            mediaPlayer.pause()
            playbackPos = mediaPlayer.currentPosition
            isPaused = true
        }
    }
    fun stop(view: View){
        if (::mediaPlayer.isInitialized){
            mediaPlayer.stop()
            mediaPlayer.release()
            playbackPos = 0
            isPaused = false
        }
        else {
            //do nothing
        }
    }



    private fun onNavigationItemSelected(item: MenuItem): Boolean{
        when (item.itemId){
            R.id.home ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.music -> {
                return true
            }
            R.id.logout ->{
                if(::mediaPlayer.isInitialized) {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                    playbackPos = 0
                    isPaused = false
                }
                val intent = Intent(this, LoginActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }
        return false
    }
}