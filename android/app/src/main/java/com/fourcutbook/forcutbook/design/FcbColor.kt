package com.fourcutbook.forcutbook.design

import androidx.compose.ui.graphics.Color

data class FcbColor(
    val fcbLightBeige: Color = Color(0xFFE1E3CB),
    val fcbLightBeige02: Color = Color(0xFFAEB090),
    val fcbDarkBeige: Color = Color(0xFFC6C8AC),
    val fcbDarkBeige02: Color = Color(0xFF4D4F29),
    val fcbGray: Color = Color(0xFFF8F9FA),
    val fcbBlack: Color = Color(0xFF000000),
    val fcbWhite: Color = Color(0xFFFFFFFF)
)

/**
 * todo: 다크 모드와 라이트 모드를 구분한다.
 */
val lightColor = FcbColor()
val darkColor = FcbColor()
