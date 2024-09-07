package com.fourcutbook.forcutbook.data.mapper

import android.graphics.Bitmap
import com.fourcutbook.forcutbook.data.response.DiariesResponse
import com.fourcutbook.forcutbook.data.response.DiaryDetailResponse
import com.fourcutbook.forcutbook.data.service.AIDiaryResponse
import com.fourcutbook.forcutbook.domain.Diary
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime

object DiaryMapper {

    fun DiariesResponse.toDomain(): List<Diary> = diaries.map {
        Diary(
            userId = it.userId,
            nickname = it.nickname,
            diaryId = it.diaryId,
            title = it.title,
            contents = it.content,
            date = it.date.toJavaLocalDateTime(),
            imageUrl = it.imageUrl,
            image = null
        )
    }

    fun AIDiaryResponse.toDomain(image: Bitmap): Diary = Diary(
        diaryId = -1,
        title = title,
        contents = content,
        date = LocalDateTime.now(),
        image = image
    )

    fun DiaryDetailResponse.toDomain(): Diary = Diary(
        userId = -1,
        diaryId = diaryId,
        title = title,
        contents = contents,
        date = date.toJavaLocalDateTime(),
        image = null,
        imageUrl = images.first().imageUrl
    )
}
