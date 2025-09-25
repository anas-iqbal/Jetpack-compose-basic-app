package com.example.luminorassignment.data

import android.content.Context

class SessionManager(private val context: Context) {

    private val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOGGED_IN_EMAIL = "logged_in_user_email"
    }

    fun saveLoggedInUserEmail(email: String) {
        with(sharedPref.edit()) {
            putString(KEY_LOGGED_IN_EMAIL, email)
            apply()
        }
    }

    fun getLoggedInUserEmail(): String? {
        return sharedPref.getString(KEY_LOGGED_IN_EMAIL, null)
    }

    fun clearLoggedInUserEmail() {
        with(sharedPref.edit()) {
            remove(KEY_LOGGED_IN_EMAIL)
            apply()
        }
    }
}
