package info.fekri.ui

import android.app.AlertDialog
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnSliderTouchListener
import info.fekri.R
import info.fekri.databinding.ActivityMainBinding
import info.fekri.databinding.DialogMessageBinding
import info.fekri.ux.NetworkChecker
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var timer: Timer
    private var isPlaying = true
    private var userChanged = false
    private var isMute = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (NetworkChecker(this).isInternetConnected) {
            prepareMusic()

            binding.btnPlayPause.setOnClickListener { configMusic() }
            binding.btnVolumeOnOff.setOnClickListener { configVolume() }
            binding.btnSkipNext.setOnClickListener { skipNext() }
            binding.btnSkipPrevious.setOnClickListener { skipPrevious() }

            binding.sliderMain.addOnChangeListener { slider, value, fromUser ->
                binding.txtLeft.text = convertMillsToString(value.toLong())
                userChanged = fromUser
            }
            binding.sliderMain.addOnSliderTouchListener(object : OnSliderTouchListener {

                override fun onStartTrackingTouch(slider: Slider) {
                    // Actually I'm not gonna use this meth :-)
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    // seek by touching -->
                    mediaPlayer.seekTo(slider.value.toInt())
                }

            })

        } else {
            // show error message(dialog) -->
            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogMessageBinding.inflate(layoutInflater)

            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()
        }
    }
    /*
    * when you go out of the application it will even play
    * so, it shouldn't happen.
    * the solution -->
    * */
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel() // cancel the timer :-)
        mediaPlayer.release() // release the mediaPlayer
    }

    private fun prepareMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_file)
        mediaPlayer.start() // start to play
        isPlaying = true
        // set play icon -->
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)

        // slider -->
        // set valueTo of slider -->
        binding.sliderMain.valueTo =
            mediaPlayer.duration.toFloat() // duration: gives us the length of the music

        binding.txtRight.text = convertMillsToString(duration = mediaPlayer.duration.toLong())

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { // you can make change on MainThread by runOnUiThread
                    if (!userChanged)
                        binding.sliderMain.value = mediaPlayer.currentPosition.toFloat()
                }
            }
        }, 1000, 1000)

        // play again the music
        mediaPlayer.isLooping = true

    }

    // This method converts mill seconds to 'String'
    private fun convertMillsToString(duration: Long): String {

        // get seconds -->
        val second = duration / 1000 % 60

        // get minute -->
        val minute = duration / (1000 * 60) % 60

        // return by set the format -->
        return java.lang.String.format(Locale.US, "%02d:%02d", minute, second) // exa: 02:50
    }

    private fun skipPrevious() {

        val present = mediaPlayer.currentPosition
        val newValue = present - 15000
        mediaPlayer.seekTo(newValue)

    }
    private fun skipNext() {

        val present = mediaPlayer.currentPosition
        val newValue = present + 15000
        mediaPlayer.seekTo(newValue)

    }

    private fun configVolume() {

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        isMute = if (isMute) {
            // un-mute the sound -->
            audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI)
            binding.btnVolumeOnOff.setImageResource(R.drawable.ic_volume_on)
            false
        } else {
            // mute the sound -->
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI)
            binding.btnVolumeOnOff.setImageResource(R.drawable.ic_volume_off)
            true
        }

    }
    private fun configMusic() {
        isPlaying =
            if (isPlaying) {
                mediaPlayer.pause()
                binding.btnPlayPause.setImageResource(R.drawable.ic_play)
                false
            } else {
                mediaPlayer.start()
                binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
                true
            }
    }

}