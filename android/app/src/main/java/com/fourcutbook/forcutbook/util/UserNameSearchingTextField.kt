package com.fourcutbook.forcutbook.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme

@Composable
fun NicknameSearchingTextField(
    modifier: Modifier = Modifier,
    value: String = " ",
    onValueChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    isFocused: Boolean = false,
    placeHolderString: String = stringResource(R.string.user_searching_placeholder)
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        cursorBrush = SolidColor(FcbTheme.colors.fcbBlack),
        textStyle = FcbTheme.typography.body.copy(color = FcbTheme.colors.fcbBlack),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            onSearch()
            keyboardController?.hide()
        },
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(44.dp)
                    .background(
                        color = FcbTheme.colors.fcbWhite,
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    )
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    painter = painterResource(id = R.drawable.ic_user_searching),
                    tint = FcbTheme.colors.fcbLightBeige,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(3.dp))
                if (!isFocused && value.isEmpty()) {
                    Text(
                        style = FcbTheme.typography.body,
                        text = placeHolderString,
                        color = FcbTheme.colors.fcbBlack
                    )
                }
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun NicknameSearchingTextFieldPreview() {
    NicknameSearchingTextField()
}
