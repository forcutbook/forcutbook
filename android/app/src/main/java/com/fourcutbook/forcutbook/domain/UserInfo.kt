package com.fourcutbook.forcutbook.domain

data class UserInfo(
    val diaries: List<Diary>,
    val subscribingCount: SubscribingCount
) {
    val diaryCount: Int
        get() = diaries.size
}
