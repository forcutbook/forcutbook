package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.DiariesResponse
import com.fourcutbook.forcutbook.domain.Diary
import kotlinx.datetime.toJavaLocalDateTime

object DiaryMapper {

    fun DiariesResponse.toDomain(): List<Diary> = diaries.map {
        Diary(
            title = it.title,
            contents = it.content,
            date = it.date.toJavaLocalDateTime(),
            image = null
        )
    }
}
