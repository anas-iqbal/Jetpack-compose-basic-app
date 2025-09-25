Project Overview
This project is an Android app built with Jetpack Compose for user authentication. It features a modern, scalable architecture with reusable UI components, state management best practices, and support for both login and registration flows.

Features
Login & Registration: Secure user authentication with email and password.

Persistence: Session state is stored via SharedPreferences (SessionManager), so users stay logged in after a restart.

User Storage: Uses Room database (UserRepository, UserDao, and User entity) for storing user credentials.

UI/UX:

Built entirely in Jetpack Compose.

Reusable text input and error components.

Tests: Repository/data layer is unit tested(not all cases)

Architecture I tried to follow

MVVM with clean separation:

ViewModel: Manages state and business logic.

Repository: Handles data access and session persistence.

UI (Screens/Components): Pure Jetpack Compose, all UI logic composable and reusable.

Main Components
UserRepository: Database operations via Room (register, query users).

SessionManager: Reads/writes session state (logged-in user) in SharedPreferences.

UserViewModel: Exposes login/register logic and session state as Compose state flows.



