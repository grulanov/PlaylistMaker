package com.practicum.playlistmaker.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ErrorViewBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    data class ViewModel(
        val state: State,
        @StringRes val title: Int,
        @StringRes val description: Int? = null,
        @StringRes val actionTitle: Int? = null,
        val onAction: (() -> Unit)? = null
    ) {
        enum class State(@DrawableRes val image: Int) {
            EMPTY(R.drawable.ic_empty_list),
            CONNECTION_ERROR(R.drawable.ic_connection_error)
        }
    }

    private var binding: ErrorViewBinding = ErrorViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var onActionButtonClick: (() -> Unit)? = null

    init {
        binding.actionButton.setOnClickListener {
            onActionButtonClick?.invoke()
        }
    }

    fun configure(viewModel: ViewModel) {
        binding.errorImage.setImageResource(viewModel.state.image)

        binding.titleText.text = context.getText(viewModel.title)

        if (viewModel.description != null) {
            binding.descriptionText.text = context.getText(viewModel.description)
        }
        binding.descriptionText.isVisible = viewModel.description != null

        if (viewModel.actionTitle != null) {
            binding.actionButton.text = context.getText(viewModel.actionTitle)
        }
        binding.actionButton.isVisible = viewModel.actionTitle != null

        onActionButtonClick = viewModel.onAction
    }

}