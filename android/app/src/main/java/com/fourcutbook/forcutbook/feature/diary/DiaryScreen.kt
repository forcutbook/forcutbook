package com.fourcutbook.forcutbook.feature.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.feature.diaryRegstration.DiaryRegistrationUiState
import com.fourcutbook.forcutbook.feature.diaryRegstration.DiaryRegistrationViewModel
import com.fourcutbook.forcutbook.util.DiaryFixture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * screen we can confirm diary created by artificial intelligence
 * todo: need to change name of screen
 */
@Composable
fun DiaryRoute(
    onShowSnackBar: (message: String) -> Unit,
    navigateToHomeScreen: (newDiary: Diary) -> Unit = {},
    diaryRegistrationViewModel: DiaryRegistrationViewModel
) {
    val uiState by diaryRegistrationViewModel.uiState.collectAsStateWithLifecycle()

    var state by remember {
        mutableStateOf<Boolean>(false)
    }
    CoroutineScope(Dispatchers.Main).launch {
        delay(2000)
        state = true
    }
    DiaryScreen(
        state = state,
        onShowSnackBar = onShowSnackBar,
        uiState = uiState,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    state: Boolean = false,
    uiState: DiaryRegistrationUiState,
    onShowSnackBar: (message: String) -> Unit = {},
    navigateToHomeScreen: (newDiary: Diary) -> Unit = {}
) {
    val diary = Diary(
        title = "호수에서 헤엄치는 오리 두마리",
        contents = "오늘 아침 산책 중에 호수에서 우아하게 헤엄치는 백조 두 마리를 보았다. 물 위로 반사된 건물들과 함께 평화로운 장면이 인상 깊었다. 잠시 멈춰서 자연의 아름다움을 만끽하며 마음의 여유를 찾았다.",
        date = LocalDateTime.now()
    )

    if (!state) {
        onShowSnackBar("이미지를 바탕으로 일기를 생성중입니다.")
        Box(
            modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(color = Color.White, trackColor = Color(0xFF1DA1F2))
        }
    } else {
        when (uiState) {
            is DiaryRegistrationUiState.Created -> Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(top = 10.dp, start = 20.dp, bottom = 10.dp),
                    text = uiState.diary.title
                )
                Text(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(top = 10.dp, start = 20.dp, bottom = 10.dp, end = 20.dp)
                        .padding(),
                    text = uiState.diary.contents
                )
                Image(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    bitmap = uiState.diary.image!!.asImageBitmap(),
                    contentDescription = null
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .offset(y = 200.dp),
                    onClick = { navigateToHomeScreen(diary) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
                ) {
                    Text("등록하기")
                }
            }
            else -> {}
        }
//        Column(
//            modifier = Modifier
//                .padding(top = 20.dp, start = 30.dp, end = 30.dp)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        ) {
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White, RoundedCornerShape(10.dp))
//                    .padding(top = 10.dp, start = 20.dp, bottom = 10.dp),
//                text = diary.title
//            )
//            Text(
//                modifier = Modifier
//                    .padding(top = 20.dp)
//                    .fillMaxWidth()
//                    .background(Color.White, RoundedCornerShape(10.dp))
//                    .padding(top = 10.dp, start = 20.dp, bottom = 10.dp, end = 20.dp)
//                    .padding(),
//                text = diary.contents
//            )
//            Image(
//                modifier = Modifier
//                    .padding(top = 20.dp)
//                    .size(100.dp)
//                    .clip(RoundedCornerShape(10.dp)),
//                painter = painterResource(id = R.drawable.for_demo),
//                contentDescription = null
//            )
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp)
//                    .offset(y = 200.dp),
//                onClick = { navigateToHomeScreen(diary) },
//                shape = RoundedCornerShape(10.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
//            ) {
//                Text("등록하기")
//            }
//        }
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun DiaryPreview() {
    DiaryScreen(uiState = DiaryRegistrationUiState.Created(DiaryFixture.get().first()))
}
