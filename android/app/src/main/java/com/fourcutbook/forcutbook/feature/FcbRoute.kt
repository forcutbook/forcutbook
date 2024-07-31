package com.fourcutbook.forcutbook.feature

import com.forcutbook.forcutbook.R

enum class FcbRoute(
    val header: Int,
    val value: String
) {
    LOGIN_ROUTE(
        header = R.string.string_header_of_home_screen,
        value = "LOGIN_ROUTE"
    ),

    HOME_ROUTE(
        header = R.string.string_header_of_home_screen,
        value = "HOME_ROUTE"
    ),

    DIARY_IMAGE_UPLOADING_ROUTE(
        header = R.string.string_header_of_image_uploading_screen,
        value = "DIARY_IMAGE_UPLOADING_ROUTE"
    ),

    DIARY_REGISTRATION_ROUTE(
        header = R.string.string_header_of_diary_registration_screen,
        value = "DIARY_REGISTRATION_ROUTE"
    );

    companion object {
        fun find(route: String?): FcbRoute? {
            if (route == null) return null
            return entries.find { it.value == route }
        }
    }
}
