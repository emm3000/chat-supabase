package com.emm.bingo.features.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.bingo.features.auth.domain.Email
import com.emm.bingo.features.auth.domain.Password
import com.emm.bingo.features.auth.domain.UserAuthentication
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoginViewModel(
    private val userAuthentication: UserAuthentication,
) : ViewModel() {

    private val _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.None)
    val state: StateFlow<LoginUiState> get() = _state.asStateFlow()

    var emailState by mutableStateOf("")
        private set

    var passwordState by mutableStateOf("")
        private set

    init {
        combine(
            snapshotFlow { emailState },
            snapshotFlow { passwordState },
            ::validateEmailAndPassword
        ).launchIn(viewModelScope)
    }

    private fun validateEmailAndPassword(email: String, password: String) = try {
        Email(email); Password(password)
        _state.value = LoginUiState.None
    } catch (e: IllegalArgumentException) {
        _state.value = LoginUiState.Error(e.message.orEmpty())
    }

    fun updateEmail(value: String) {
        emailState = value
    }

    fun updatePassword(value: String) {
        passwordState = value
    }

    fun auth(email: String, password: String) = viewModelScope.launch {
        _state.value = LoginUiState.Loading
        tryAuth(email, password)
    }

    private suspend fun tryAuth(email: String, password: String) {
        _state.value = try {
            val authenticate: UserInfo = userAuthentication.authenticate(email, password) ?: return
            val userJson = Json.encodeToString(authenticate)
            LoginUiState.Success(userJson)
        } catch (throwable: Throwable) {
            LoginUiState.Error(throwable.message.orEmpty())
        }
    }
}

sealed interface LoginUiState {

    data object Loading : LoginUiState

    data class Error(val error: String) : LoginUiState

    data class Success(val info: String) : LoginUiState

    data object None : LoginUiState
}