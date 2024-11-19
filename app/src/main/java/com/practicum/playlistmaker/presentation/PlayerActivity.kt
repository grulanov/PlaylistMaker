package com.practicum.playlistmaker.presentation

import android.os.Bundle
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
import com.practicum.playlistmaker.logic.domainModels.Track

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var track: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        track = intent.getParcelableExtra<Track>("track") ?: throw NullPointerException()

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

        binding.trackNameTextView.text = track.trackName ?: "-"
        binding.artistNameTextView.text = track.artistName ?: "-"
        binding.trackTimeProgressTextView.text = "0:30" // TODO
        binding.trackTimeValueTextView.text = track.trackTime ?: "-"
        binding.yearValueTextView.text = track.releaseYear?.toString() ?: "-"
        binding.genreValueTextView.text = track.primaryGenreName ?: "-"
        binding.countryValueTextView.text = track.country ?: "-"

        binding.collectionValueTextView.text = track.collectionName
        binding.collectionTitleTextView.isVisible = track.collectionName != null
        binding.collectionValueTextView.isVisible = track.collectionName != null

        Glide.with(binding.root)
            .load(track.artworkUrl500?.toUri())
            .placeholder(R.drawable.placeholder_player_artwork)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius_s)))
            .into(binding.trackImageView)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}