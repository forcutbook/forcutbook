package com.fourcutbook.forcutbook.util

import com.fourcutbook.forcutbook.domain.Diary
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object DiaryFixture{

    fun get(): List<Diary> = listOf(
        Diary(
            title = "고기를 먹었다.",
            contents = "오늘은 친구들과 함께 삼겹살을 먹었다..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.now()
            )
        ), Diary(
            title = "호수에 산책을 갔다.",
            contents = "가족들과 함께 집 뒤에 있는 호수에..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(13, 10)
            )
        ),
        Diary(
            title = "커피를 마셨다",
            contents = "자몽 허니 블랙티를 마셨다..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(9, 45)
            )
        ),
        Diary(
            title = "고기를 먹었다.",
            contents = "오늘은 친구들과 함께 삼겹살을 먹었다..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.now()
            )
        ), Diary(
            title = "호수에 산책을 갔다.",
            contents = "가족들과 함께 집 뒤에 있는 호수에..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(13, 10)
            )
        ),
        Diary(
            title = "커피를 마셨다",
            contents = "자몽 허니 블랙티를 마셨다..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(9, 45)
            )
        ),
    )
}