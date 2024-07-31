package com.fourcutbook.forcutbook.design

import androidx.compose.ui.graphics.Color

data class FcbColor(
    val fcbLightBeige: Color = Color(0xFFE1E3CB),
    val fcbDarkBeige: Color = Color(0xFFC6C8AC),
    val fcbGray: Color = Color(0xFFF8F9FA),
    val fcbBlack: Color = Color(0xFF000000)
)

/**
 * todo: 다크 모드와 라이트 모드를 구분한다.
 */
val lightColor = FcbColor()
val darkColor = FcbColor()
