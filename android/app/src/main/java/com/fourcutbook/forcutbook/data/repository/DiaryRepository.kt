package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.Diary
import java.io.File

interface DiaryRepository {

    suspend fun fetchDiaries(): List<Diary>

    suspend fun fetchDiaryDetails(diaryId: Long): Diary

    suspend fun postImage(image: File): Diary
}
