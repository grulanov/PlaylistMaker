package com.practicum.playlistmaker.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.practicum.playlistmaker.logic.repositories.TracksRepository
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.logic.domainModels.Track
import com.practicum.playlistmaker.logic.repositories.SearchHistoryRepository
import com.practicum.playlistmaker.presentation.PlayerActivity
import com.practicum.playlistmaker.presentation.common.ErrorView

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchTracksAdapter = SearchTracksAdapter()
    private val tracksRepository = TracksRepository.create()
    private val searchHistoryRepository = SearchHistoryRepository.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val paddingBottom = if (ime.bottom > 0) ime.bottom else systemBars.bottom
            v.setPadding (
                systemBars. left, systemBars.top, systemBars.right,
                paddingBottom
            )
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.recyclerView.adapter = searchTracksAdapter

        binding.clearButton.setOnClickListener {
            binding.searchEditText.setText("")
            showContentState(emptyList())
            hideKeyboard()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                configureClearButtonVisibility(p0)
                if (binding.searchEditText.hasFocus() && p0?.isEmpty() == true) {
                    showHistoryState()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })
        binding.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (textView.text.toString().isNotEmpty()) {
                    searchTracks(textView.text.toString())
                }
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.searchEditText.setOnFocusChangeListener { view, hasFocus ->
            if (binding.searchEditText.text.isEmpty()) {
                if (hasFocus) showHistoryState() else showContentState(emptyList())
            }
        }

        configureClearButtonVisibility(null)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun configureClearButtonVisibility(s: CharSequence?) {
        binding.clearButton.isVisible = !s.isNullOrEmpty()
    }

    private fun searchTracks(query: String) {
        showProgress()
        tracksRepository.searchTracks(query) {
            it.fold(onSuccess = { tracks ->
                if (tracks.isEmpty()) {
                    showNothingFoundState()
                } else {
                    showContentState(tracks)
                }
            }, onFailure = {
                showConnectionErrorState()
            })
        }
    }

    private fun showContentState(tracks: List<Track>) {
        searchTracksAdapter.items = tracks.map {
            SearchListItem.TrackItem(it) {
                handleTrackClick(it)
            }
        }
        showErrorView(null)
    }

    private fun showHistoryState() {
        val historyTracks: List<Track> = searchHistoryRepository.tracksSearchHistory

        val items: MutableList<SearchListItem> = mutableListOf(
            SearchListItem.SpacingItem(resources.getDimensionPixelSize(R.dimen.margin_xl)),
            SearchListItem.HeaderItem(getString(R.string.search_search_history_title))
        )
        items.addAll(
            historyTracks.map {
                SearchListItem.TrackItem(it) {
                    handleTrackClick(it)
                    showHistoryState()
                }
            }
        )
        items.add(SearchListItem.ActionButtonItem(getString(R.string.search_search_history_clear_action)) {
            searchHistoryRepository.clearHistory()
            showHistoryState()
        })

        searchTracksAdapter.items = if (historyTracks.isNotEmpty()) items else emptyList()
        showErrorView(null)
    }

    private fun showNothingFoundState() {
        showErrorView(
            ErrorView.ViewModel(
                ErrorView.ViewModel.State.EMPTY,
                R.string.error_empty_list_title
            )
        )
    }

    private fun showConnectionErrorState() {
        showErrorView(
            ErrorView.ViewModel(
                ErrorView.ViewModel.State.CONNECTION_ERROR,
                R.string.error_connection_title,
                R.string.error_connection_description,
                R.string.error_connection_retry_action
            ) {
                searchTracks(binding.searchEditText.text.toString())
            }
        )
    }

    private fun showErrorView(viewModel: ErrorView.ViewModel?) {
        viewModel?.let {
            binding.errorView.configure(it)
        }
        binding.errorView.isVisible = viewModel != null
        binding.recyclerView.isVisible = viewModel == null
        binding.progressBarContainer.isVisible = false
    }

    private fun showProgress() {
        binding.errorView.isVisible = false
        binding.recyclerView.isVisible = false
        binding.progressBarContainer.isVisible = true
    }

    private fun handleTrackClick(track: Track) {
        searchHistoryRepository.didSelectTrack(track)

        val intent = Intent(this@SearchActivity, PlayerActivity::class.java)
        intent.putExtra("track", track)
        startActivity(intent)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus()
    }
}