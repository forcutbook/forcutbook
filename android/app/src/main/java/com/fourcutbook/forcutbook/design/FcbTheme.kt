package com.fourcutbook.forcutbook.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalCustomColors =
    staticCompositionLocalOf {
        FcbColor()
    }
val LocalCustomTypography =
    staticCompositionLocalOf {
        FcbTypography()
    }
val LocalCustomPadding =
    staticCompositionLocalOf {
        FcbPadding()
    }

@Composable
fun FcbTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            darkTheme -> darkColor
            else -> lightColor
        }
    CompositionLocalProvider(
        LocalCustomColors provides colorScheme,
        LocalCustomTypography provides FcbTypography(),
        LocalCustomPadding provides FcbPadding(),
        content = content
    )
}

object FcbTheme {
    val colors: FcbColor
        @Composable
        get() = LocalCustomColors.current
    val typography: FcbTypography
        @Composable
        get() = LocalCustomTypography.current
    val padding: FcbPadding
        @Composable
        get() = LocalCustomPadding.current
}
