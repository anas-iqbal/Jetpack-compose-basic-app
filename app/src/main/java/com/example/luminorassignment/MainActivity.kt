package com.example.luminorassignment

import UserViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.luminorassignment.ui.screens.dashboard.DashboardScreen
import com.example.luminorassignment.ui.screens.login.LoginScreen
import com.example.luminorassignment.ui.screens.registration.RegistrationScreen
import com.example.luminorassignment.ui.theme.LuminorAssignmentTheme


class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuminorAssignmentTheme {
                val navController = rememberNavController()
                val userEmail by userViewModel.userEmail.collectAsState()

                val startDestination = if (userEmail.isNullOrEmpty()) "login" else "dashboard"

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                userViewModel = userViewModel,
                                onLoginSuccess = {
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onRegisterClick = {
                                    navController.navigate("register")
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable("register") {
                            RegistrationScreen(
                                userViewModel = userViewModel,
                                onBackToLoginClick = {
                                    navController.popBackStack()
                                },
                                onRegistrationSuccess = {
                                    navController.popBackStack()
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable("dashboard") {
                            DashboardScreen(
                                userEmail = userEmail ?: "",
                                onLogout = {
                                    userViewModel.logout {
                                        navController.navigate("login") {
                                            popUpTo("dashboard") { inclusive = true }
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
