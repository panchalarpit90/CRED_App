package com.example.cred.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.cred.R
import com.example.cred.ui.StatusApi

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: StatusApi?) {
    when (status) {
        StatusApi.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_error)
        }
        StatusApi.LOADED, null -> {
            statusImageView.visibility = View.GONE
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}


@BindingAdapter("progressStatus")
fun bindProgress(progressBar: ProgressBar, status: StatusApi?) {
    progressBar.visibility = if (status == StatusApi.LOADING) View.VISIBLE else View.GONE
}
@BindingAdapter("cardStatus")
fun bindCardVisibility(constraintLayout: ConstraintLayout, status: StatusApi?) {
    constraintLayout.visibility = if (status == StatusApi.LOADED) View.VISIBLE else View.GONE
}

@BindingAdapter("boldTitleText", "regularSubtitleText")
fun setFormattedText(radioButton: RadioButton, title: String?, subtitle: String?) {
    if (title == null || subtitle == null) return
    val context = radioButton.context
    val titleColor = ContextCompat.getColor(context, R.color.lightText)
    val subtitleColor = ContextCompat.getColor(context, R.color.greyText)
    val subtitleSizeInPx = context.resources.getDimensionPixelSize(com.intuit.ssp.R.dimen._9ssp)
    val formattedText = SpannableStringBuilder().apply {
        append(title)
        setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(ForegroundColorSpan(titleColor), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        append("\n$subtitle")
        setSpan(ForegroundColorSpan(subtitleColor), title.length + 1, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(AbsoluteSizeSpan(subtitleSizeInPx), title.length + 1, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    radioButton.text = formattedText
}
