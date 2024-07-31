package com.fourcutbook.forcutbook.feature

import com.forcutbook.forcutbook.R

enum class FcbBottomNavigationItem(
    val icon: Int,
    val route: String
) {

    HOME(R.drawable.ic_home, FcbRoute.HOME_ROUTE.value),
    SEARCHING(R.drawable.ic_searching, ""),
    DIARY_IMAGE_UPLOADING(R.drawable.ic_posting, FcbRoute.DIARY_IMAGE_UPLOADING_ROUTE.value),
    MY_PAGE(R.drawable.ic_my_page, ""),
    SETTING(R.drawable.ic_setting, "")
}
