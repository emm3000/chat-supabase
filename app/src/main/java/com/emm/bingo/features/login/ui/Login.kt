package com.emm.bingo.features.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.emm.bingo.ui.theme.BingoTheme

@Composable
fun Login() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "bingoApp")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    BingoTheme {
        Login()
    }
}