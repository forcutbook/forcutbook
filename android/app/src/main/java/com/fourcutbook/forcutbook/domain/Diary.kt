package com.fourcutbook.forcutbook.domain

import java.time.LocalDateTime

data class Diary(
    val title: String,
    val contents: String,
    val date: LocalDateTime
)