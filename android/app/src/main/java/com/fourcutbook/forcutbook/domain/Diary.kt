package com.fourcutbook.forcutbook.domain

import android.graphics.Bitmap
import java.time.LocalDateTime

data class Diary(
    val title: String,
    val contents: String,
    val date: LocalDateTime,
    var image: Bitmap? = null
)
