package com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithBackButton
import com.fourcutbook.forcutbook.feature.diaryposting.DiaryPostingUiState
import com.fourcutbook.forcutbook.feature.diaryposting.DiaryPostingViewModel
import com.fourcutbook.forcutbook.util.DiaryFixture
import com.fourcutbook.forcutbook.util.toFile
import java.io.File

/**
 * screen we can confirm diary created by artificial intelligence and registry
 * todo: need to change name of screen
 */
@Composable
fun DiaryRegistrationRoute(
    diaryPostingViewModel: DiaryPostingViewModel,
    onDiaryRegistered: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    onShowSnackBar: (message: String) -> Unit = {}
) {
    // todo: State 모르고 지금 쓰고 있음, collectAsStateWithLifecycle()~~~~~~~~~~~
    val uiState by diaryPostingViewModel.uiState.collectAsStateWithLifecycle()

    // todo: 모르는거
    DiaryRegistrationScreen(
        uiState = uiState,
        onDiaryRegistry = diaryPostingViewModel::postDiary,
        onDiaryRegistered = onDiaryRegistered,
        onBackPressed = onBackPressed,
        onShowSnackBar = onShowSnackBar
    )
}

@Composable
fun DiaryRegistrationScreen(
    modifier: Modifier = Modifier,
    uiState: DiaryPostingUiState,
    onDiaryRegistry: (diary: Diary, image: File) -> Unit = { _, _ -> },
    onDiaryRegistered: () -> Unit = {},
    onDiaryRegistryCancel: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    onShowSnackBar: (message: String) -> Unit = {}
) {
    val context = LocalContext.current

    when (uiState) {
        is DiaryPostingUiState.IncludingImage.ImageUploaded -> {
            Column(
                modifier = modifier
                    .padding(
                        top = FcbTheme.padding.basicVerticalPadding,
                        start = FcbTheme.padding.basicHorizontalPadding,
                        end = FcbTheme.padding.basicHorizontalPadding
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                FcbTopAppBarWithBackButton(
                    title = stringResource(id = R.string.header_of_diary_registration_screen),
                    onBackClick = onBackPressed
                )
                DiaryTitle(
                    modifier = Modifier.padding(top = FcbTheme.padding.basicVerticalPadding),
                    title = uiState.diary.title
                )
                DiaryContents(contents = uiState.diary.contents)

                uiState.diary
                    .image
                    ?.let { DiaryImage(image = it) }

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
        }

        is DiaryPostingUiState.Registered -> onDiaryRegistered()

        else -> {}
    }
}

@Composable
fun DiaryTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        style = FcbTheme.typography.title,
        // todo: 일관성 없는 코드.. 어느 곳에서는 Scaffold로 처리하고...
        text = title
    )
}

@Composable
fun DiaryImage(image: Bitmap) {
    Image(
        modifier = Modifier
            .padding(top = FcbTheme.padding.smallVerticalPadding)
            .size(120.dp)
            .clip(RoundedCornerShape(5.dp)),
        bitmap = image.asImageBitmap(),
        contentDescription = null
    )
}

@Composable
fun DiaryImage(imageUrl: String) {
    AsyncImage(
        modifier = Modifier
            .padding(top = FcbTheme.padding.smallVerticalPadding)
            .size(120.dp)
            .clip(RoundedCornerShape(5.dp)),
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun DiaryContents(contents: String) {
    Text(
        modifier = Modifier
            .padding(top = FcbTheme.padding.smallVerticalPadding)
            .fillMaxWidth(),
        style = FcbTheme.typography.body,
        text = contents
    )
}

@Composable
fun DiaryRegistrationButton(onDiaryRegistry: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = FcbTheme.padding.smallVerticalPadding),
        onClick = { onDiaryRegistry() },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FcbTheme.colors.fcbDarkBeige)
    ) {
        Text(
            style = FcbTheme.typography.body,
            color = FcbTheme.colors.fcbBlack,
            text = stringResource(R.string.string_registration_button_description)
        )
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun DiaryRegistrationPreview() {
    DiaryRegistrationScreen(
        modifier = Modifier.background(FcbTheme.colors.fcbGray),
        uiState = DiaryPostingUiState.IncludingImage.ImageUploaded(DiaryFixture.get().first())
    )
}
