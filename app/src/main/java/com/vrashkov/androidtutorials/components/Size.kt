package com.tutorial.scaleforallsizes

import androidx.compose.ui.unit.*

val width: Float = 375f
val height: Float = 812f

inline fun Float.toWidth() : Float {
    if (AppConfig.widthPixels == 1)  {
        return this
    }
    val screen: Int = AppConfig.widthPixels
    val current: Float = this
    val calc: Float = (screen * ((current / width))) / AppConfig.density
    return calc
}
inline fun Float.toHeight() : Float {
    if (AppConfig.heightPixels == 1)  {
        return this
    }
    val screen: Int = AppConfig.heightPixels
    val current: Float = this
    val calc: Float = (screen * ((current / height))) / AppConfig.density
    return calc
}

inline fun Float.toSize() : Float {
    if (AppConfig.heightPixels == 1)  {
        return this
    }
    val screen: Int = AppConfig.heightPixels
    val current: Float = this
    val calc: Float = (screen * (current / height))  / AppConfig.fontDensity
    return calc
}

val Int._dpw: Dp get() = if (this == 0) Dp(0f) else Dp(this.toFloat().toWidth())
val Int._dph: Dp get() = if (this == 0) Dp(0f) else Dp(this.toFloat().toHeight())
@OptIn(ExperimentalUnitApi::class)
val Int._sp: TextUnit get() = if (this == 0)  TextUnit(0f, TextUnitType.Sp)  else TextUnit(this.toFloat().toSize(), TextUnitType.Sp)
