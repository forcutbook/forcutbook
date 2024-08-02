package com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.feature.diaryposting.DiaryPostingUiState
import com.fourcutbook.forcutbook.feature.diaryposting.DiaryPostingViewModel
import com.fourcutbook.forcutbook.util.parseBitmap
import com.fourcutbook.forcutbook.util.toFile
import java.io.File

@Composable
fun DiaryImageUploadingRoute(
    diaryPostingViewModel: DiaryPostingViewModel = hiltViewModel(),
    navigateToDiaryScreen: () -> Unit = {}
) {
    val uiState by diaryPostingViewModel.uiState.collectAsStateWithLifecycle()

    DiaryImageUploadingScreen(
        uiState = uiState,
        onImageUpload = { imageFile, imageBitmap ->
            diaryPostingViewModel.postImage(
                imageFile = imageFile,
                imageBitmap = imageBitmap
            )
        },
        navigateToDiaryScreen = navigateToDiaryScreen
    )
}

@Composable
fun DiaryImageUploadingScreen(
    uiState: DiaryPostingUiState,
    onImageUpload: (
        imageFile: File,
        imageBitmap: Bitmap
    ) -> Unit = { _, _ -> },
    navigateToDiaryScreen: () -> Unit = {}
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    when (uiState) {
        is DiaryPostingUiState.ImageUploaded -> navigateToDiaryScreen()

        is DiaryPostingUiState.ImageUploading -> {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                val context = LocalContext.current
                val takePhotoFromAlbumLauncher = rememberAlbumLauncher(
                    onSuccess = { image ->
                        bitmap = image
                    }
                )

                UploadingImage(
                    onClick = {
                        takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                    },
                    bitmap = bitmap
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                        bitmap?.let { image ->
                            onImageUpload(image.toFile(context), image)
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
                ) {
                    Text(text = "AI 일기")
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
                ) {
                    Text(text = "수동 일기")
                }
            }
        }

        is DiaryPostingUiState.LoadingForUploading -> {

        }

        else -> {

        }
    }
}

@Composable
fun UploadingImage(
    onClick: () -> Unit,
    bitmap: Bitmap? = null
) {
    val painter: Painter = painterResource(R.drawable.ic_empty_image)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        bitmap?.let {
            Image(
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                bitmap = it.asImageBitmap(),
                contentDescription = null
            )
        } ?: Image(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    onClick()
                },
            painter = painter,
            contentDescription = null
        )
    }
}

private val takePhotoFromAlbumIntent =
    Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
        type = "image/*"
        action = Intent.ACTION_GET_CONTENT
        putExtra(
            Intent.EXTRA_MIME_TYPES,
            arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
        )
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
    }

@Composable
fun rememberAlbumLauncher(
    onSuccess: (image: Bitmap) -> Unit,
    onFailure: () -> Unit = {},
    onCanceled: () -> Unit = {}
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                onSuccess(uri.parseBitmap(context))
            } ?: onFailure()
        } else if (result.resultCode != Activity.RESULT_CANCELED) {
            onCanceled()
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun ImageUploadingScreenPreview() {
    DiaryImageUploadingScreen(DiaryPostingUiState.ImageUploading)
}
