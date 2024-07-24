package com.fourcutbook.forcutbook.data.repository

import android.graphics.BitmapFactory
import android.util.Log
import com.fourcutbook.forcutbook.data.mapper.DiaryMapper.toDomain
import com.fourcutbook.forcutbook.data.service.DiaryService
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.util.DiaryFixture
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor(
    private val userRepository: UserRepository,
    private val diaryService: DiaryService
) : DiaryRepository {

    override suspend fun fetchDiaries(): List<Diary> {
        run {
            val userId = userRepository.fetchUserId().first()
            val response = diaryService.fetchDiaries(userId)

            if (response.isSuccessful) {
                val diaries = response.body()
                Log.d("woogi", "fetchDiaries: $diaries")
                return diaries?.toDomain() ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun fetchDiaryDetails(diaryId: Long): Diary {
        return DiaryFixture.get().first()
    }

    override suspend fun postImage(image: File): Diary {
//        val map = HashMap<String, RequestBody>()
//        val diary = DiaryFixture.get().first()
//        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), image)
//
//        diaryService.test(
//            title = diary.title,
//            content = diary.contents,
//            image = MultipartBody.Part.createFormData("file", image.name, requestFile)
//        )

        return DiaryFixture.get().first().copy(image = BitmapFactory.decodeFile(image.absolutePath))
    }
}
