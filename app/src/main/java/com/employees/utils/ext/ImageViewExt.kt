package com.employees.utils.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.employees.R
import com.employees.utils.GlideApp

private const val CROSS_FADE_DURATION = 400

fun ImageView.setCircleImageWithGlide(
    @DrawableRes
    placeHolder: Int = R.drawable.ic_account_circle,
    uri: String
) {
    GlideApp
        .with(this.context)
        .load(uri)
        .circleCrop()
        .placeholder(placeHolder)
        .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}