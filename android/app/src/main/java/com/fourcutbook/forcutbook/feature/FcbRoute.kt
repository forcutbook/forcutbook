package com.fourcutbook.forcutbook.feature

import com.forcutbook.forcutbook.R

sealed interface FcbRoute {

    val header: Int?
    val value: String

    data object LoginRoute : FcbRoute {

        override val header = R.string.string_header_of_home_screen
        override val value = "LOGIN_ROUTE"
    }

    data object HomeRoute : FcbRoute {

        override val header = R.string.string_header_of_home_screen
        override val value = "HOME_ROUTE"
    }

    data object DiaryImageUploadingRoute : FcbRoute {

        override val header = R.string.string_header_of_image_uploading_screen
        override val value = "DIARY_IMAGE_UPLOADING_ROUTE"
    }

    data object DiaryRegistrationRoute : FcbRoute {

        override val header = R.string.string_header_of_diary_registration_screen
        override val value = "DIARY_REGISTRATION_ROUTE"
    }

    companion object {

        private val bottomNavigationItems = listOf(
            LoginRoute,
            HomeRoute,
            DiaryImageUploadingRoute
            // todo: settingRoute 추가
        )

        fun find(route: String?): FcbRoute? {
            if (route == null) return null
            return bottomNavigationItems.find { it.value == route }
        }
    }
}
