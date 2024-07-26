package com.fourcutbook.forcutbook.feature.diaryRegistration

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingUiState
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingViewModel
import com.fourcutbook.forcutbook.util.DiaryFixture
import com.fourcutbook.forcutbook.util.toFile
import java.io.File

/**
 * screen we can confirm diary created by artificial intelligence and registry
 * todo: need to change name of screen
 */
@Composable
fun DiaryRegistrationRoute(
    imageUploadingViewModel: ImageUploadingViewModel,
    onShowSnackBar: (message: String) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val uiState by imageUploadingViewModel.uiState.collectAsStateWithLifecycle()

    DiaryRegistrationScreen(
        uiState = uiState,
        onDiaryRegistry = imageUploadingViewModel::postDiary,
        navigateToHomeScreen = navigateToHomeScreen,
        onShowSnackBar = onShowSnackBar
    )
}

@Composable
fun DiaryRegistrationScreen(
    modifier: Modifier = Modifier,
    uiState: ImageUploadingUiState,
    onDiaryRegistry: (diary: Diary, image: File) -> Unit = { _, _ -> },
    navigateToHomeScreen: () -> Unit = {},
    onShowSnackBar: (message: String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val context = LocalContext.current

        when (uiState) {
            is ImageUploadingUiState.Uploaded -> {
                DiaryTitle(title = uiState.diary.title)
                DiaryContents(contents = uiState.diary.contents)
                uiState.diary.image?.let { image ->
                    DiaryImage(image = image)
                }
                DiaryRegistrationButton(
                    onDiaryRegistry = {
                        uiState.diary
                            .image
                            ?.let { imageBitmap ->
                                onDiaryRegistry(
                                    uiState.diary,
                                    imageBitmap.toFile(context)
                                )
                            }
                    }
                )
            }

            is ImageUploadingUiState.Done -> navigateToHomeScreen()

            else -> {}
        }
    }
}

@Composable
fun DiaryTitle(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(top = 10.dp, start = 20.dp, bottom = 10.dp),
        text = title
    )
}

@Composable
fun DiaryContents(contents: String) {
    Text(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(top = 10.dp, start = 20.dp, bottom = 10.dp, end = 20.dp)
            .padding(),
        text = contents
    )
}

@Composable
fun DiaryImage(image: Bitmap) {
    Image(
        modifier = Modifier
            .padding(top = 20.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp)),
        bitmap = image.asImageBitmap(),
        contentDescription = null
    )
}

@Composable
fun DiaryRegistrationButton(onDiaryRegistry: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .offset(y = 200.dp),
        onClick = { onDiaryRegistry() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
    ) {
        Text("등록하기")
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun DiaryRegistrationPreview() {
    DiaryRegistrationScreen(uiState = ImageUploadingUiState.Uploaded(DiaryFixture.get().first()))
}
