package com.tutorial.scaleforallsizes

import android.content.Context
import android.content.res.Configuration

object AppConfig {

    var density = 1f
    var fontDensity = 1f
    var widthPixels = 1
    var heightPixels = 1

    fun onConfigChanged(context: Context) {

        density = context.resources.displayMetrics.density
        widthPixels = context.resources.displayMetrics.widthPixels
        heightPixels = context.resources.displayMetrics.heightPixels
        fontDensity = context.resources.displayMetrics.scaledDensity
    }
}