package com.fourcutbook.forcutbook.design

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.forcutbook.forcutbook.R
import javax.annotation.concurrent.Immutable

val maruburiFont = FontFamily(
    Font(
        resId = R.font.maruburi_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.maruburi_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.maruburi_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.maruburi_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.maruburi_extralight,
        weight = FontWeight.ExtraLight
    )
)

@Immutable
data class FcbTypography(
    val heading: TextStyle =
        TextStyle(
            fontFamily = maruburiFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
    val title: TextStyle =
        TextStyle(
            fontFamily = maruburiFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    val footer: TextStyle =
        TextStyle(
            fontFamily = maruburiFont,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 12.sp
        ),
    val body: TextStyle =
        TextStyle(
            fontFamily = maruburiFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
)
