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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithOnlyTitle
import com.fourcutbook.forcutbook.util.noRippleClickable
import com.fourcutbook.forcutbook.util.parseBitmap
import com.fourcutbook.forcutbook.util.toFile
import java.io.File

@Composable
fun DiaryImageUploadingRoute(
    onImageUploaded: (
        title: String,
        contents: String,
        filePath: String
    ) -> Unit = { _, _, _ -> },
    viewModel: DiaryImageUploadingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = null) {
        viewModel.event.collect { event ->
            when (event) {
                is DiaryImageUploadingEvent.ImageUploaded -> onImageUploaded(
                    event.title,
                    event.contents,
                    event.filePath
                )

                else -> {
                }
            }
        }
    }

    DiaryImageUploadingScreen(
        uiState = uiState,
        onImageSelect = { imageBitmap ->
            viewModel.selectImage(imageBitmap)
        },
        onImageUploaded = { imageFile, imageBitmap ->
            viewModel.postImage(
                imageFile = imageFile,
                imageBitmap = imageBitmap
            )
        }
    )
}

@Composable
fun DiaryImageUploadingScreen(
    uiState: DiaryImageUploadingUiState,
    onImageSelect: (imageBitmap: Bitmap) -> Unit = {},
    onImageUploaded: (
        imageFile: File,
        imageBitmap: Bitmap
    ) -> Unit = { _, _ -> }
) {
    when (uiState) {
        is DiaryImageUploadingUiState.ImageSelecting -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = FcbTheme.padding.basicVerticalPadding)
            ) {
                val context = LocalContext.current
                val takePhotoFromAlbumLauncher = rememberAlbumLauncher(
                    onSuccess = { image ->
                        onImageSelect(image)
                    }
                )
                FcbTopAppBarWithOnlyTitle(title = stringResource(R.string.string_header_of_image_uploading_screen))
                UploadingImage(
                    modifier = Modifier.padding(top = FcbTheme.padding.basicVerticalPadding),
                    onClick = {
                        takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                    },
                    bitmap = uiState.bitmap
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                        uiState.bitmap?.let { image ->
                            onImageUploaded(image.toFile(context), image)
                        }
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FcbTheme.colors.fcbDarkBeige,
                        contentColor = FcbTheme.colors.fcbBlack,
                        disabledContainerColor = FcbTheme.colors.fcbDarkBeige02,
                        disabledContentColor = FcbTheme.colors.fcbDarkBeige02
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_ai_diary),
                        style = FcbTheme.typography.body
                    )
                }
            }
        }

        is DiaryImageUploadingUiState.LoadingForUploading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterHorizontally),
                    color = FcbTheme.colors.fcbDarkBeige02
                )
                Text(
                    modifier = Modifier
                        .padding(top = FcbTheme.padding.basicVerticalPadding)
                        .fillMaxWidth(),
                    text = stringResource(R.string.loading_for_creating_diary),
                    style = FcbTheme.typography.body,
                    textAlign = TextAlign.Center
                )
            }
        }

        else -> {
        }
    }
}

@Composable
fun UploadingImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    bitmap: Bitmap? = null
) {
    val painter: Painter = painterResource(R.drawable.ic_empty_image)
    Box(
        modifier = modifier
            .padding(horizontal = FcbTheme.padding.basicHorizontalPadding)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .noRippleClickable {
                onClick()
            }
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
            modifier = Modifier.align(Alignment.Center),
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

@Preview(widthDp = 360, heightDp = 640, backgroundColor = 0xFFF8F9FA)
@Composable
fun ImageUploadingScreenPreview() {
    DiaryImageUploadingScreen(
        uiState = DiaryImageUploadingUiState.ImageSelecting(null)
    )
}

@Preview(widthDp = 360, heightDp = 640, backgroundColor = 0xFFF8F9FA)
@Composable
fun LoadingForUploadingScreenPreview() {
    DiaryImageUploadingScreen(
        uiState = DiaryImageUploadingUiState.LoadingForUploading
    )
}
