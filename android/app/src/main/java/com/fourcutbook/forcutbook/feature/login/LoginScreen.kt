package com.fourcutbook.forcutbook.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.forcutbook.forcutbook.R

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    // todo: collectAsStateWithLifecycle()
    // todo: `remeber` can be persisted on configuration change?
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // todo: LaunchedEffect()
    LaunchedEffect(key1 = null) {
        loginViewModel.event.collect { event ->
            when (event) {
                is LoginEvent.Loading -> {}

                is LoginEvent.Done -> {
                    navigateToHome()
                }
            }
        }
    }

    LoginScreen(
        id = id,
        password = password,
        onEnterId = { enteringId ->
            id = enteringId
        },
        onEnterPassword = { enteringPassword ->
            password = enteringPassword
        },
        onLogin = {
            loginViewModel.postLogin(
                id = id,
                password = password
            )
        }
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    id: String = "",
    password: String = "",
    onEnterId: (enteredId: String) -> Unit = {},
    onEnterPassword: (enteredPassword: String) -> Unit = {},
    onLogin: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        PrivateTextField(
            type = PrivateType.ID,
            value = id,
            onValueChange = onEnterId,
            placeHolderString = stringResource(id = R.string.login_request_enter_id)
        )
        PrivateTextField(
            type = PrivateType.PASSWORD,
            value = password,
            onValueChange = onEnterPassword,
            placeHolderString = stringResource(R.string.login_request_enter_password)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            onClick = { onLogin() }
        ) {
            Text("로그인")
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(
        id = "kimjinuk1999",
        password = "abcd1234"
    )
}
