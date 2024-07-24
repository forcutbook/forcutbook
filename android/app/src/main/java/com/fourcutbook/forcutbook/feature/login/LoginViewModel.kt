package com.fourcutbook.forcutbook.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _event: MutableSharedFlow<LoginEvent> = MutableSharedFlow()
    val event: SharedFlow<LoginEvent>
        get() = _event.asSharedFlow()

    fun postLogin(
        id: String,
        password: String
    ) {
        viewModelScope.launch {
            flow {
                emit(
                    loginRepository.postLogin(
                        id = id,
                        password = password
                    )
                )
            }.collect {
                _event.emit(LoginEvent.Done)
            }
        }
    }
}
