package com.fourcutbook.forcutbook.data.repository

import android.graphics.BitmapFactory
import android.util.Log
import com.fourcutbook.forcutbook.data.mapper.DiaryMapper.toDomain
import com.fourcutbook.forcutbook.data.service.DiaryService
import com.fourcutbook.forcutbook.domain.Diary
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val diaryService: DiaryService
) : DiaryRepository {

    override suspend fun fetchMyDiaries(): List<Diary> {
        run {
            val id = tokenRepository.fetchUserId().first()
                ?: throw IllegalStateException("Cannot access on this contents.")
            val response = diaryService.fetchMyDiaries(id)
            Log.d("woogi", "조회하려는 유저의 : $id")
            Log.d("woogi", "fetchDiaries: $response")
            if (response.isSuccessful) {
                val diaries = response.body()?.result

                return diaries?.toDomain() ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun fetchUserDiaries(userId: Long): List<Diary> {
        val myId = tokenRepository.fetchUserId().firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")
        val response = diaryService.fetchUserDiaries(
            userId = myId,
            friendId = userId
        )
        Log.d("woogi", "fetchUserStats: $response")
        if (response.isSuccessful) {
            val diaries = response.body()?.result

            return diaries?.toDomain() ?: throw IOException("Response body is null.")
        }
        throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun fetchDiaryDetails(diaryId: Long): Diary {
        run {
            val userId = tokenRepository.fetchUserId().first()
                ?: throw IllegalStateException("Cannot access on this contents.")
            val response = diaryService.fetchDiaryDetails(
                userId = userId,
                diaryId = diaryId
            )

            if (response.isSuccessful) {
                val diaryDetail = response.body()?.result

                return diaryDetail?.toDomain() ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun postImage(image: File): Diary {
        run {
            val userId = tokenRepository
                .fetchUserId()
                .first()
                ?: throw IllegalStateException("Cannot access on this contents.")
            val imageFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val response = diaryService.postImage(
                userId = userId,
                images = MultipartBody.Part.createFormData("images", image.name, imageFile)
            )

            if (response.isSuccessful) {
                val aiDiary = response.body()?.result
                // todo: file을 다시 bitmap으로 바꾸지 않고 viewModel에서 bitmap을 유지한다던가 해야할듯
                return aiDiary?.toDomain(BitmapFactory.decodeFile(image.path))
                    ?: throw IOException("Response body is null.")
            }
            throw IOException("Request failed with code ${response.code()}!")
        }
    }

    override suspend fun postDiary(diary: Diary, image: File) {
        run {
            val userId = tokenRepository.fetchUserId().first()
                ?: throw IllegalStateException("Cannot access on this contents.")
            val imageFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val title = diary.title.toRequestBody("text/plain".toMediaTypeOrNull())
            val contents = diary.contents.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = diaryService.postDiary(
                userId = userId,
                title = title,
                content = contents,
                image = listOf(MultipartBody.Part.createFormData("images", image.name, imageFile))
            )
            Log.d("woogi", "postDiary: $response")
        }
    }
}
