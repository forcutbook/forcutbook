package com.fourcutbook.forcutbook.feature

import androidx.annotation.StringRes
import com.forcutbook.forcutbook.R

sealed interface FcbRoute {

    // todo: get?
    @get:StringRes
    val headerRes: Int
    val value: String

    data object LoginRoute : FcbRoute {

        override val headerRes = R.string.header_of_home_screen
        override val value = "LOGIN_ROUTE"
    }

    data object DiaryFeed : FcbRoute {

        override val headerRes = R.string.header_of_home_screen
        override val value = "DIARY_FEED"
    }

    data object DiaryImageUploadingRoute : FcbRoute {

        override val headerRes = R.string.string_header_of_image_uploading_screen
        override val value = "DIARY_IMAGE_UPLOADING_ROUTE"
    }

    data object DiaryRegistrationRoute : FcbRoute {

        override val headerRes = R.string.header_of_diary_registration_screen
        override val value = "DIARY_REGISTRATION_ROUTE"
    }

    data object MyPageRoute : FcbRoute {

        override val headerRes = R.string.header_of_my_page
        override val value: String = "MY_PAGE"
    }

    data object UserPageRoute : FcbRoute {

        override val headerRes = R.string.header_of_user_page
        override val value: String = "USER_PAGE"
    }

    data object FollowingListRoute : FcbRoute {

        override val headerRes: Int = R.string.header_of_subscribing_diary
        override val value: String = "SUBSCRIBING_DIARY"
    }

    data object FollowerListRoute : FcbRoute {

        override val headerRes: Int = R.string.header_of_subscribing_diary
        override val value: String = "SUBSCRIBED_DIARY"
    }

    data object NotificationRoute : FcbRoute {

        override val headerRes: Int = R.string.header_of_notification
        override val value: String = "SUBSCRIBING_DIARY"
    }

    data object UserSearchingRoute : FcbRoute {
        override val headerRes: Int = R.string.header_of_user_searching
        override val value: String = "USER_SERACHING"
    }

    companion object {

        private val bottomNavigationItems = listOf(

            DiaryFeed,
            DiaryImageUploadingRoute,
            MyPageRoute
            // todo: settingRoute 추가
        )

        fun find(route: String?): FcbRoute? {
            if (route == null) return null
            return bottomNavigationItems.find { it.value == route }
        }
    }
}
