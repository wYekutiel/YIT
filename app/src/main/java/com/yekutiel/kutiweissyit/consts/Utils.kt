package com.yekutiel.kutiweissyit.consts

import android.content.Context
import android.content.res.Configuration

const val LARGE_FIXED_IMAGE_HEIGHT = 300f
const val NORMAL_FIXED_IMAGE_HEIGHT = 200f
const val SMALL_FIXED_IMAGE_HEIGHT = 150f

fun getFixedImageHeight(context: Context?): Float {
    context ?: return NORMAL_FIXED_IMAGE_HEIGHT

    val screenSize: Int = context.resources.configuration.screenLayout and
            Configuration.SCREENLAYOUT_SIZE_MASK

    return when (screenSize) {
        Configuration.SCREENLAYOUT_SIZE_LARGE -> LARGE_FIXED_IMAGE_HEIGHT
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> NORMAL_FIXED_IMAGE_HEIGHT
        Configuration.SCREENLAYOUT_SIZE_SMALL -> SMALL_FIXED_IMAGE_HEIGHT
        else -> NORMAL_FIXED_IMAGE_HEIGHT
    }
}
