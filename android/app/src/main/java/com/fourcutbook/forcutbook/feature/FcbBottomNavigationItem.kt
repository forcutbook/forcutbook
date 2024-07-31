package com.fourcutbook.forcutbook.feature

import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.feature.home.HOME_ROUTE
import com.fourcutbook.forcutbook.feature.imageUploading.IMAGE_UPLOADING_ROUTE

enum class FcbBottomNavigationItem(
    val icon: Int,
    val route: String
) {

    HOME(R.drawable.ic_home, HOME_ROUTE),
    SEARCHING(R.drawable.ic_searching, ""),
    POSTING(R.drawable.ic_posting, IMAGE_UPLOADING_ROUTE),
    MY_PAGE(R.drawable.ic_my_page, ""),
    SETTING(R.drawable.ic_setting, "")
}
