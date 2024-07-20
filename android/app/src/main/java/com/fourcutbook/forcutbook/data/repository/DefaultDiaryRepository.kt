package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.service.DiaryService
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.util.DiaryFixture
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor(
    private val diaryService: DiaryService
) : DiaryRepository {

    override suspend fun fetchDiaries(): List<Diary> {
        return DiaryFixture.get()
    }

    override suspend fun fetchDiaryDetails(diaryId: Long): Diary {
        return DiaryFixture.get().first()
    }

    override suspend fun createAIDiaries(diary: Diary) {
    }
}
