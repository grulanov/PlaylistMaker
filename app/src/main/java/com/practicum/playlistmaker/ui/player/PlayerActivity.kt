package com.practicum.playlistmaker.ui.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    enum class PlayerState {
        IDLE, PREPARED, PLAYING, PAUSED
    }

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var track: Track

    private val mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.IDLE
    private val playerTimeProgressDateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

    private val handler = Handler(Looper.getMainLooper())

    private val trackProgressUpdateRunnable = object : Runnable {
        override fun run() {
            if (playerState != PlayerState.PLAYING) {
                return
            }
            updateTrackTimeProgress(mediaPlayer.currentPosition)
            handler.postDelayed(this, TRACK_PROGRESS_UPDATE_INTERVAL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        track = intent.getParcelableExtra<Track>(INTENT_TRACK_KEY) ?: throw NullPointerException()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.playPauseButton.setImageResource(R.drawable.ic_play)
        binding.playPauseButton.isEnabled = false
        binding.playPauseButton.setOnClickListener {
            onPlayPauseButtonTap()
        }

        binding.trackNameTextView.text = track.trackName ?: NO_INFO_PLACEHOLDER
        binding.artistNameTextView.text = track.artistName ?: NO_INFO_PLACEHOLDER
        binding.trackTimeValueTextView.text = track.trackTime ?: NO_INFO_PLACEHOLDER
        binding.yearValueTextView.text = track.releaseYear?.toString() ?: NO_INFO_PLACEHOLDER
        binding.genreValueTextView.text = track.primaryGenreName ?: NO_INFO_PLACEHOLDER
        binding.countryValueTextView.text = track.country ?: NO_INFO_PLACEHOLDER

        binding.collectionValueTextView.text = track.collectionName
        binding.collectionTitleTextView.isVisible = track.collectionName != null
        binding.collectionValueTextView.isVisible = track.collectionName != null

        Glide.with(binding.root)
            .load(track.artworkUrl500?.toUri())
            .placeholder(R.drawable.placeholder_player_artwork)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius_s)))
            .into(binding.trackImageView)

        updateTrackTimeProgress(0)
        preparePlayer()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(trackProgressUpdateRunnable)
        mediaPlayer.release()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            resetPlayer()
        }
        mediaPlayer.setOnCompletionListener {
            resetPlayer()
        }
    }

    private fun resetPlayer() {
        binding.playPauseButton.isEnabled = true
        binding.playPauseButton.setImageResource(R.drawable.ic_play)
        playerState = PlayerState.PREPARED
        updateTrackTimeProgress(0)
    }

    private fun startPlayer() {
        mediaPlayer.start()
        binding.playPauseButton.setImageResource(R.drawable.ic_pause)
        playerState = PlayerState.PLAYING
        handler.post(trackProgressUpdateRunnable)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        binding.playPauseButton.setImageResource(R.drawable.ic_play)
        playerState = PlayerState.PAUSED
    }

    private fun onPlayPauseButtonTap() {
        when(playerState) {
            PlayerState.IDLE -> {} // Do nothing
            PlayerState.PREPARED, PlayerState.PAUSED -> startPlayer()
            PlayerState.PLAYING -> pausePlayer()
        }
    }

    private fun updateTrackTimeProgress(currentProgress: Int) {
        binding.trackTimeProgressTextView.text = playerTimeProgressDateFormat.format(currentProgress)
    }

    companion object {
        const val INTENT_TRACK_KEY = "track"

        private const val NO_INFO_PLACEHOLDER = "-"
        private const val TRACK_PROGRESS_UPDATE_INTERVAL = 300L
    }
}