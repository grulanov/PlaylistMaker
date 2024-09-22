package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SettingsActivity : AppCompatActivity() {
    private val backButton: ImageButton by lazy {
        findViewById<ImageButton>(R.id.back_button)
    }

    private val darkThemeSwitch: SwitchCompat by lazy {
        findViewById<SwitchCompat>(R.id.dark_theme_switch)
    }

    private val shareListItem: View by lazy {
        findViewById<View>(R.id.share_list_item)
    }

    private val supportListItem: View by lazy {
        findViewById<View>(R.id.support_list_item)
    }

    private val userAgreementListItem: View by lazy {
        findViewById<View>(R.id.user_agreement_list_item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        shareListItem.setOnClickListener {
            shareApp()
        }

        supportListItem.setOnClickListener {
            sendMessageToSupport()
        }

        userAgreementListItem.setOnClickListener {
            openUserAgreement()
        }
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