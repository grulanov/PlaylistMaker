package com.practicum.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility

class SearchActivity : AppCompatActivity() {
    private val backButton: ImageButton by lazy {
        findViewById<ImageButton>(R.id.back_button)
    }

    private val clearButton: ImageButton by lazy {
        findViewById<ImageButton>(R.id.clear_button)
    }

    private val searchEditText: EditText by lazy {
        findViewById<EditText>(R.id.search_edit_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        clearButton.setOnClickListener {
            searchEditText.setText("")
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                configureClearButtonVisibility(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })

        configureClearButtonVisibility(null)
    }

    private fun configureClearButtonVisibility(s: CharSequence?) {
        clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
    }
}