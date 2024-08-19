package com.fourcutbook.forcutbook.data.repository

import android.graphics.BitmapFactory
import com.fourcutbook.forcutbook.data.mapper.DiaryMapper.toDomain
import com.fourcutbook.forcutbook.data.service.DiaryService
import com.fourcutbook.forcutbook.domain.Diary
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor(
    private val userRepository: UserRepository,
    private val diaryService: DiaryService
) : DiaryRepository {

    override suspend fun fetchDiaries(userId: Long?): List<Diary> {
        run {
            val id = userId ?: userRepository.fetchUserId().first()
            val response = diaryService.fetchDiaries(id)

            if (response.isSuccessful) {
                val diaries = response.body()

                return diaries?.toDomain() ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun fetchDiaryDetails(diaryId: Long): Diary {
        run {
            val userId = userRepository.fetchUserId().first()
            val response = diaryService.fetchDiaryDetails(
                userId = userId,
                diaryId = diaryId
            )

            if (response.isSuccessful) {
                val diaryDetail = response.body()

                return diaryDetail?.toDomain() ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun postImage(image: File): Diary {
        run {
            val userId = userRepository.fetchUserId().first()
            val imageFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val response = diaryService.postImage(
                userId = userId,
                image = listOf(MultipartBody.Part.createFormData("image", image.name, imageFile))
            )

            if (response.isSuccessful) {
                val aiDiary = response.body()
                // todo: file을 다시 bitmap으로 바꾸지 않고 viewModel에서 bitmap을 유지한다던가 해야할듯
                return aiDiary?.toDomain(BitmapFactory.decodeFile(image.path))
                    ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun postDiary(diary: Diary, image: File) {
        run {
            val userId = userRepository.fetchUserId().first()
            val imageFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val title = diary.title.toRequestBody("text/plain".toMediaTypeOrNull())
            val contents = diary.contents.toRequestBody("text/plain".toMediaTypeOrNull())
            val friends =
                listOf<Long>().joinToString(",").toRequestBody("text/plain".toMediaTypeOrNull())
            val date = diary.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                .toRequestBody("text/plain".toMediaTypeOrNull())

            val response = diaryService.postDiary(
                userId = userId,
                title = title,
                content = contents,
                image = listOf(MultipartBody.Part.createFormData("images", image.name, imageFile)),
                friends = friends
            )
        }
    }
}
