package com.fourcutbook.forcutbook.data.repository

import android.graphics.Bitmap
import com.fourcutbook.forcutbook.domain.Diary

interface DiaryRepository {

    suspend fun fetchDiaries(): List<Diary>

    suspend fun fetchDiaryDetails(diaryId: Long): Diary

    suspend fun createAIDiaries(image: Bitmap): Diary
}
