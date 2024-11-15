package com.practicum.playlistmaker.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding
import com.practicum.playlistmaker.logic.servicies.AppThemeServiceImpl


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val appThemeService = AppThemeServiceImpl.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.darkThemeSwitch.isChecked = appThemeService.isDarkTheme
        binding.darkThemeSwitch.setOnCheckedChangeListener { _, checked ->
            appThemeService.isDarkTheme = checked
        }

        binding.shareListItem.setOnClickListener {
            shareApp()
        }

        binding.supportListItem.setOnClickListener {
            sendMessageToSupport()
        }

        binding.userAgreementListItem.setOnClickListener {
            openUserAgreement()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun shareApp() {
        val shareAppIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_share_link))
        }
        val shareAppIntentChooser = Intent.createChooser(shareAppIntent, null)
        startActivity(shareAppIntentChooser)
    }

    private fun sendMessageToSupport() {
        val sendMessageToSupportIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.settings_support_request_email)))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_support_request_title))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_support_request_message))
        }
        startActivity(sendMessageToSupportIntent)
    }

    private fun openUserAgreement() {
        val openUserAgreementIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.settings_user_agreement_link))
        }
        startActivity(openUserAgreementIntent)
    }
}