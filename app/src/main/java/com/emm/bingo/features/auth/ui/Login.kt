package com.emm.bingo.features.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emm.bingo.features.shared.ui.theme.BingoTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun Login(
    navController: NavController,
    loginViewModel: LoginViewModel = koinViewModel(),
) {

    val state: LoginUiState by loginViewModel.state.collectAsState()

    Login(
        state = state,
        email = loginViewModel.emailState,
        navigateToHome = {
            navController.navigate("home") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        },
        updateEmail = loginViewModel::updateEmail,
        password = loginViewModel.passwordState,
        updatePassword = loginViewModel::updatePassword,
        onClickLogin = loginViewModel::auth
    )
}

@Composable
private fun Login(
    state: LoginUiState,
    email: String = "",
    navigateToHome: () -> Unit = {},
    updateEmail: (String) -> Unit = {},
    password: String = "",
    updatePassword: (String) -> Unit = {},
    onClickLogin: (String, String) -> Unit = { _, _ -> }
) {

    LaunchedEffect(Unit) {
        if (state is LoginUiState.Success) {
            navigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeadSection()

        Spacer(modifier = Modifier.height(26.dp))

        MinimalistEditText(
            labelText = "Email",
            placeholderText = "Ingresa tu correo electronico",
            email = email,
            updateEmail = updateEmail,
        )

        Spacer(modifier = Modifier.height(10.dp))

        MinimalistEditText(
            labelText = "Contraseña",
            placeholderText = "Ingresa tu contraseña",
            email = password,
            updateEmail = updatePassword,
        )

        when (state) {
            is LoginUiState.Error -> Recommendation(state.error)
            is LoginUiState.Loading -> CircularProgressIndicator(modifier = Modifier.height(30.dp))
            else -> Spacer(modifier = Modifier.height(30.dp))
        }

        SimpleButton(
            buttonText = "INGRESAR",
            isEnabled = state !is LoginUiState.Error,
            onClickLogin = { onClickLogin(email, password) }
        )

        O()

        SimpleButton(
            buttonText = "REGISTRARSE",
            isEnabled = state !is LoginUiState.Error,
            onClickLogin = { onClickLogin(email, password) }
        )
    }
}

@Composable
private fun Recommendation(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(vertical = 30.dp),
        fontFamily = FontFamily.Monospace,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        color = Color.Red
    )
}

@Composable
private fun O() {
    Text(
        text = "------ O ------",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(vertical = 10.dp),
        fontFamily = FontFamily.Monospace,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SimpleButton(
    buttonText: String,
    isEnabled: Boolean,
    onClickLogin: () -> Unit,
) {

    val rememberText = remember {
        buttonText.map { "$it" }.joinToString(" ")
    }

    OutlinedButton(
        onClick = onClickLogin,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = isEnabled,
    ) {
        Text(
            text = rememberText,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun MinimalistEditText(
    labelText: String,
    placeholderText: String,
    email: String,
    updateEmail: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = updateEmail,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = {
            Text(text = labelText, fontFamily = FontFamily.Monospace)
        },
        placeholder = {
            Text(text = placeholderText, fontFamily = FontFamily.Monospace)
        }
    )
}

@Composable
private fun HeadSection() {
    Text(
        text = "BingoApp",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    BingoTheme {
        Login(LoginUiState.None) { _, _ -> }
    }
}