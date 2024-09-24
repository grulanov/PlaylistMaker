package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SettingsActivity : AppCompatActivity() {
    private val toolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val darkThemeSwitch: SwitchCompat by lazy {
        findViewById<SwitchCompat>(R.id.dark_theme_switch)
    }

    private val shareListItem: TextView by lazy {
        findViewById<TextView>(R.id.share_list_item)
    }

    private val supportListItem: TextView by lazy {
        findViewById<TextView>(R.id.support_list_item)
    }

    private val userAgreementListItem: TextView by lazy {
        findViewById<TextView>(R.id.user_agreement_list_item)
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

        setSupportActionBar(toolbar)

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