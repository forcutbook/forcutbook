package com.fourcutbook.forcutbook.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme

// todo: 계속해서 반복되는 코드 공통적인 component로 분리가 필요
@Composable
fun FcbTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit = { }
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(20.dp),
            onClick = onBackClick
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = FcbTheme.padding.basicHorizontalPadding),
            style = FcbTheme.typography.heading,
            // todo: 일관성 없는 코드.. 어느 곳에서는 Scaffold로 처리하고...
            text = title
        )
    }
}

@Preview
@Composable
fun FcbTopAppBarPreview() {
    FcbTopAppBar(
        modifier = Modifier.background(FcbTheme.colors.fcbGray),
        title = "알림"
    )
}
