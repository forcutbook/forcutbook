package com.fourcutbook.forcutbook.feature.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.util.DiaryFixture
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(newDiary: Diary? = null) {
    HomeScreen(newDiary = newDiary)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    diaries: List<Diary> = DiaryFixture.get(),
    newDiary: Diary? = null,
) {


    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        HomeCalendar()
        HomeDiariesColumn(diaries = newDiary?.run {
            listOf(this) + diaries
        } ?: diaries)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeDiariesColumn(
    diaries: List<Diary>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(diaries) { diary ->
            HomeDiaryItem(diary = diary)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeDiaryItem(
    diary: Diary,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp, start = 30.dp, end = 30.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                tint = Color(0xFF1DA1F2),
                painter = painterResource(id = R.drawable.ic_diaries),
                contentDescription = null
            )
            Text(
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 4.dp),
                text = diary.date.format(DateTimeFormatter.ofPattern("HH:mm")),
                fontSize = 13.sp
            )
            Text(
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp),
                text = diary.title
            )
        }
        Text(
            modifier = Modifier.padding(top = 6.dp, start = 30.dp, end = 30.dp, bottom = 16.dp),
            text = diary.contents
        )
    }
}

@SuppressLint("RememberReturnType")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeCalendar(
    //calendarState: CalendarState
) {
    //todo: ViewModel로 분리
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )
    Box(
        modifier = Modifier
            .padding(top = 16.dp, start = 30.dp, end = 30.dp)
            //todo: clip, background 차이 구분
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        HorizontalCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = state,
            dayContent = { Day(it) },
            monthHeader = { month ->
                val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 10.dp),
                    text = "${LocalDate.now().year}, ${month.yearMonth.month}",
                    fontWeight = FontWeight.SemiBold
                )
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            },
            monthBody = { _, content ->
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    // Render the provided content!
                    content()
                }
            },
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(day: CalendarDay) {
    if (day.date == LocalDate.now()) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(
                    shape = CircleShape,
                    color = Color(0xFF1DA1F2)
                ),// This is important for square sizing!
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontWeight = FontWeight.SemiBold,
                text = day.date.dayOfMonth.toString(),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    } else {
        Box(
            modifier = Modifier.aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontWeight = FontWeight.SemiBold,
                text = day.date.dayOfMonth.toString(),
                fontSize = 16.sp,
                color = if (day.position == DayPosition.MonthDate) Color.Black else Color.White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Month(month: CalendarMonth) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = month.toString(),
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(widthDp = 360, heightDp = 640)
@Composable
fun HomePreview() {
    HomeScreen(
        diaries = DiaryFixture.get()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun DiaryPreview() {
    HomeDiaryItem(
        diary = Diary(
            title = "종강하는 날",
            contents = "졸업프로젝트1 최종 발표를 마지막으로..",
            date = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.now()
            )
        )
    )
}
