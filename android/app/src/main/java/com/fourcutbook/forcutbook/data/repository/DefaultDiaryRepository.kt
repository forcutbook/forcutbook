package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.util.DiaryFixture
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor() : DiaryRepository {

    override suspend fun fetchDiaries(): List<Diary> {
        return DiaryFixture.get()
    }
}
