package com.fourcutbook.forcutbook.domain

import android.graphics.Bitmap
import java.time.LocalDateTime

data class Diary(
    val userId: Long = -1,
    val nickname: String = "",
    val diaryId: Long,
    val title: String,
    val contents: String,
    val date: LocalDateTime,
    val image: Bitmap? = null,
    val imageUrl: String = ""
)
