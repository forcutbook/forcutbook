package com.fourcutbook.forcutbook.feature.diaryRegistration

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingUiState
import com.fourcutbook.forcutbook.feature.imageUploading.ImageUploadingViewModel
import com.fourcutbook.forcutbook.util.DiaryFixture

/**
 * screen we can confirm diary created by artificial intelligence and registry
 * todo: need to change name of screen
 */
@Composable
fun DiaryRegistrationRoute(
    imageUploadingViewModel: ImageUploadingViewModel,
    uploadedImage: Bitmap,
    onShowSnackBar: (message: String) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val uiState by imageUploadingViewModel.uiState.collectAsStateWithLifecycle()

    DiaryRegistrationScreen(
        uiState = uiState,
        uploadedImage = uploadedImage,
        onShowSnackBar = onShowSnackBar,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@Composable
fun DiaryRegistrationScreen(
    modifier: Modifier = Modifier,
    uiState: ImageUploadingUiState,
    uploadedImage: Bitmap,
    onShowSnackBar: (message: String) -> Unit = {},
    navigateToHomeScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (uiState) {
            is ImageUploadingUiState.Uploaded -> {
                DiaryTitle(title = uiState.diary.title)
                DiaryContents(
                    contents = uiState.diary.contents,
                    image = uploadedImage
                )
                DiaryRegistrationButton(navigateToHomeScreen = navigateToHomeScreen)
            }

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
fun DiaryContents(
    contents: String,
    image: Bitmap
) {
    Text(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(top = 10.dp, start = 20.dp, bottom = 10.dp, end = 20.dp)
            .padding(),
        text = contents
    )
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
fun DiaryRegistrationButton(navigateToHomeScreen: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .offset(y = 200.dp),
        onClick = { navigateToHomeScreen() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
    ) {
        Text("등록하기")
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun DiaryRegistrationPreview() {
    val context = LocalContext.current

    DiaryRegistrationScreen(
        uiState = ImageUploadingUiState.Uploaded(DiaryFixture.get().first()),
        uploadedImage = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.demo_image
        )
    )
}
