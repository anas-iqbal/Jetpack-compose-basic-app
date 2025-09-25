import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.luminorassignment.data.local.AppDatabase
import com.example.luminorassignment.data.local.user.User
import com.example.luminorassignment.data.local.user.UserDao
import com.example.luminorassignment.data.repositories.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        repository = UserRepository(userDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser_getUserByEmail() = runTest {
        val user = User(email = "test@example.com", password = "password")
        repository.registerUser(user)
        val fetchedUser = repository.getUserByEmail("test@example.com")
        assertNotNull(fetchedUser)
        assertEquals("test@example.com", fetchedUser?.email)
    }

    @Test
    fun getUserByEmail_noUserFound() = runTest {
        val user = repository.getUserByEmail("nonexisting@example.com")
        assertNull(user)
    }
}
