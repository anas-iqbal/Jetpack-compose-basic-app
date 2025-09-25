package com.example.luminorassignment.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SessionManagerTest {

    private lateinit var sessionManager: SessionManager
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        sessionManager = SessionManager(context)
        sessionManager.clearLoggedInUserEmail()
    }

    @Test
    fun saveLoggedInUserEmail() {
        val email = "user@example.com"
        sessionManager.saveLoggedInUserEmail(email)
        val savedEmail = sessionManager.getLoggedInUserEmail()
        assertEquals(email, savedEmail)
    }
}
