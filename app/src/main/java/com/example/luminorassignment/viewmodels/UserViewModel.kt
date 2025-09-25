import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.luminorassignment.data.SessionManager
import com.example.luminorassignment.data.local.AppDatabase
import com.example.luminorassignment.data.local.user.User
import com.example.luminorassignment.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    private val repository = UserRepository(db.userDao())
    private val sessionManager = SessionManager(application.applicationContext)

    private val _userEmail = MutableStateFlow<String?>(sessionManager.getLoggedInUserEmail())
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _registrationError = MutableStateFlow<String?>(null)
    val registrationError: StateFlow<String?> = _registrationError.asStateFlow()

    fun registerUser(email: String, password: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            val existingUser = repository.getUserByEmail(email)
            if (existingUser == null) {
                repository.registerUser(User(email = email, password = password))
                sessionManager.saveLoggedInUserEmail(email)
                _userEmail.value = email
                _registrationError.value = null
                onComplete()
            } else {
                _registrationError.value = "User with this email already exists"
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null && user.password == password) {
                sessionManager.saveLoggedInUserEmail(email)
                _userEmail.value = email
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            sessionManager.clearLoggedInUserEmail()
            _userEmail.value = null
            onComplete()
        }
    }

    fun clearRegistrationError() {
        _registrationError.value = null
    }
}
