package com.fourcutbook.forcutbook.data.mapper

import android.graphics.Bitmap
import com.fourcutbook.forcutbook.data.response.DiariesResponse
import com.fourcutbook.forcutbook.data.service.AIDiaryResponse
import com.fourcutbook.forcutbook.domain.Diary
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime

object DiaryMapper {

    fun DiariesResponse.toDomain(): List<Diary> = diaries.map {
        Diary(
            title = it.title,
            contents = it.content,
            // todo: 서버에서 non-nullable 타입으로 처리하도록 만들기ㅅㄷㄴ
            date = it.date?.toJavaLocalDateTime() ?: LocalDateTime.now(),
            image = null
        )
    }

    fun AIDiaryResponse.toDomain(image: Bitmap): Diary = Diary(
        title = title,
        contents = content,
        date = LocalDateTime.now(),
        image = image
    )
}
