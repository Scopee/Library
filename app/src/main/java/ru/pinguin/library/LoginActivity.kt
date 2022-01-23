package ru.pinguin.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.pinguin.library.database.User
import ru.pinguin.library.database.UsersDatabase
import ru.pinguin.library.database.UsersRepository
import ru.pinguin.library.databinding.ActivityLoginBinding
import ru.pinguin.library.databinding.ActivityMainBinding
import ru.pinguin.library.network.login.LoginApiService
import ru.pinguin.library.network.login.LoginRemoteRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var usersRepository: UsersRepository
    private lateinit var viewBinding: ActivityLoginBinding
    private lateinit var loginRepository: LoginRemoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val userDao = UsersDatabase.getDatabase(this).userDao()
        usersRepository = UsersRepository(userDao)
        loginRepository = LoginRemoteRepository(LoginApiService.service)
        val user = userDao.getUser()
        if (user != null) {
            goToMainActivity()
        } else {
            viewBinding.loginText.visibility = View.VISIBLE
            viewBinding.loginButton.visibility = View.VISIBLE
            viewBinding.loginButton.setOnClickListener {
                val text = viewBinding.loginText.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    loginRepository.login(text)
                    userDao.saveUser(User(text))
                    CoroutineScope(Dispatchers.Main).launch {
                        goToMainActivity()
                    }
                }
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}