package com.waslabrowser.footballapptask.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.waslabrowser.footballapptask.R

    @BindingAdapter("favoriteIcon")
    fun setFavourite(imageView : ImageView, isFavourite: Boolean? = false) {
        isFavourite?.let {
            if (isFavourite)
                imageView.setImageResource(R.drawable.ic_baseline_favorite)
            else
                imageView.setImageResource(R.drawable.ic_baseline_favorite_border)
        }
    }