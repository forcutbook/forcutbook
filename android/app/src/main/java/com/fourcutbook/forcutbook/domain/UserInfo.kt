package com.fourcutbook.forcutbook.domain

data class UserInfo(
    val nickname: String = "user1",
    val diaries: List<Diary>,
    val userId: Long,
    val subscribingCount: SubscribingCount
) {
    val diaryCount: Int
        get() = diaries.size
}
