package com.example.luminorassignment.ui.screens.login

import UserViewModel
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.luminorassignment.R
import com.example.luminorassignment.ui.components.ErrorText
import com.example.luminorassignment.ui.components.TextField

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }

    val emailError by remember(email) { derivedStateOf { email.isNotEmpty() && !validateEmail(email) } }
    val passwordError by remember(password) { derivedStateOf { password.isNotEmpty() && !validatePassword(password) } }

    val scrollState = rememberScrollState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(32.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.android_icon),
                    contentDescription = "App icon",
                    modifier = Modifier.size(82.dp)
                )
                Text(
                    text = "Authentication",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                )
            }
        }
        TextField(
            value = email,
            onValueChange = {
                email = it
                loginError = null
            },
            label = "E-mail",
            modifier = Modifier.fillMaxWidth(),
            isError = emailError,
            errorText = "Invalid email address",
            keyboardType = KeyboardType.Email
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
                loginError = null
            },
            label = "Password",
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError,
            errorText = "Password must be 6+ characters",
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password
        )

        loginError?.let {
            Spacer(Modifier.height(16.dp))
            ErrorText(it)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                if (!emailError && !passwordError && email.isNotBlank() && password.isNotBlank()) {
                    userViewModel.loginUser(email, password,
                        onSuccess = {
                            loginError = null
                            onLoginSuccess()
                        },
                        onError = {
                            loginError = "Invalid email or password"
                        }
                    )
                } else {
                    loginError = "Please enter valid credentials"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Log in", color = Color.White)
        }
        Spacer(Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
            Text(" or ", style = MaterialTheme.typography.bodyMedium)
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89CFDF))
        ) {
            Text("Register")
        }
    }
}

private fun validateEmail(input: String) =
    Patterns.EMAIL_ADDRESS.matcher(input).matches()

private fun validatePassword(input: String) =
    input.length >= 6