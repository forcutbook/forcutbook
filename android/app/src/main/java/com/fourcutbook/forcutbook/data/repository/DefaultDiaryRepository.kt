package com.fourcutbook.forcutbook.data.repository

import android.graphics.BitmapFactory
import com.fourcutbook.forcutbook.data.service.DiaryService
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.util.DiaryFixture
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor(
    private val diaryService: DiaryService
) : DiaryRepository {

    override suspend fun fetchDiaries(): List<Diary> {
        return DiaryFixture.get()
    }

    override suspend fun fetchDiaryDetails(diaryId: Long): Diary {
        return DiaryFixture.get().first()
    }

    override suspend fun postImage(image: File): Diary {
        val map = HashMap<String, RequestBody>()
        val diary = DiaryFixture.get().first()
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), image)

        diaryService.test(
            title = diary.title,
            content = diary.contents,
            image = MultipartBody.Part.createFormData("file", image.name, requestFile)
        )

        return DiaryFixture.get().first().copy(image = BitmapFactory.decodeFile(image.absolutePath))
    }
}
