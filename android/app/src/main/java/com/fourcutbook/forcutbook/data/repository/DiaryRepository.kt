package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.Diary
import java.io.File

interface DiaryRepository {

    suspend fun fetchDiaries(userId: Long? = null): List<Diary>

    suspend fun fetchDiaryDetails(diaryId: Long): Diary

    suspend fun postImage(image: File): Diary

    // todo: diary와 image file이 각각의 인자로 필요할까?
    suspend fun postDiary(diary: Diary, image: File)
}
