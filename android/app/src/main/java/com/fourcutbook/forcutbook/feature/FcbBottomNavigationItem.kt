package com.fourcutbook.forcutbook.feature

import com.forcutbook.forcutbook.R

enum class FcbBottomNavigationItem(
    val icon: Int,
    val route: String
) {

    HOME(R.drawable.ic_home, FcbRoute.DiaryFeed.value),
    SEARCHING(R.drawable.ic_searching, ""),
    DIARY_IMAGE_UPLOADING(R.drawable.ic_posting, FcbRoute.DiaryImageUploadingRoute.value),
    MY_PAGE(R.drawable.ic_my_page, FcbRoute.MyPageRoute.value),
    SETTING(R.drawable.ic_setting, "")
}
