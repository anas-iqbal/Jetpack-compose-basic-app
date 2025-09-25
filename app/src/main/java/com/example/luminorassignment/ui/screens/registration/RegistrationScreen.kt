package com.example.luminorassignment.ui.screens.registration


import UserViewModel
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.luminorassignment.ui.components.ErrorText
import com.example.luminorassignment.ui.components.TextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    userViewModel: UserViewModel,
    onBackToLoginClick: () -> Unit,
    onRegistrationSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    val emailError by remember(email) { derivedStateOf { email.isNotEmpty() && !validateEmail(email) } }
    val passwordError by remember(password) { derivedStateOf { password.isNotEmpty() && !validatePassword(password) } }
    val confirmPasswordError by remember(password, confirmPassword) {
        derivedStateOf { confirmPassword.isNotEmpty() && password != confirmPassword }
    }

    val registrationError by userViewModel.registrationError.collectAsState()
    val scrollState = rememberScrollState()

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Register", style = MaterialTheme.typography.headlineMedium) },
            navigationIcon = {
                IconButton(onClick = onBackToLoginClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(32.dp))
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    userViewModel.clearRegistrationError()
                },
                label = "E-mail",
                isError = emailError,
                errorText = "Invalid email address",
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    userViewModel.clearRegistrationError()
                },
                label = "Password",
                isError = passwordError,
                errorText = "Password must be 6+ characters",
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    userViewModel.clearRegistrationError()
                },
                label = "Confirm Password",
                isError = confirmPasswordError,
                errorText = "Passwords do not match",
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth()
            )
            if (registrationError != null) {
                Spacer(Modifier.height(16.dp))
                ErrorText(registrationError ?: "")
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    val emailValid = validateEmail(email)
                    val passwordValid = validatePassword(password)
                    val confirmPwdValid = password == confirmPassword
                    if (emailValid && passwordValid && confirmPwdValid) {
                        userViewModel.registerUser(email, password) {
                            onRegistrationSuccess()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Register", color = Color.White)
            }
        }
    }
}

private fun validateEmail(input: String) =
    Patterns.EMAIL_ADDRESS.matcher(input).matches()

private fun validatePassword(input: String) =
    input.length >= 6