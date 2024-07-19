package com.fourcutbook.forcutbook.feature.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forcutbook.forcutbook.R

@Composable
fun PrivateTextField(
    type: PrivateType,
    value: String,
    onValueChange: (value: String) -> Unit = {},
    placeHolderString: String = stringResource(R.string.login_request_enter_id)
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        value = value,
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 18.sp
        ).copy(Color.Black),
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    color = Color.Black,
                    text = placeHolderString
                )
            }

            when (type) {
                PrivateType.PASSWORD -> Text(
                    color = Color.Black,
                    text = "*".repeat(value.length)
                )

                PrivateType.ID -> innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun IdFieldPreview() {
    PrivateTextField(
        type = PrivateType.ID,
        value = "kimjinuk99"
    )
}

@Preview
@Composable
fun PasswordFieldPreview() {
    PrivateTextField(
        type = PrivateType.PASSWORD,
        value = "ABCD1234"
    )
}
