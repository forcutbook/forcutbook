package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.Diary

interface DiaryRepository {

    suspend fun fetchDiaries(): List<Diary>
}
