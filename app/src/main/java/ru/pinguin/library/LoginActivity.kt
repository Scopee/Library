package ru.pinguin.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.pinguin.library.database.User
import ru.pinguin.library.database.UsersDatabase
import ru.pinguin.library.database.UsersRepository
import ru.pinguin.library.databinding.ActivityLoginBinding
import ru.pinguin.library.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var usersRepository: UsersRepository
    private lateinit var viewBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val userDao = UsersDatabase.getDatabase(this).userDao()
        usersRepository = UsersRepository(userDao)

        val user = userDao.getUser()
        if (user != null) {
            goToMainActivity()
        } else {
            viewBinding.loginText.visibility = View.VISIBLE
            viewBinding.loginButton.visibility = View.VISIBLE
            viewBinding.loginButton.setOnClickListener {
                val text = viewBinding.loginText.text.toString()
                //TODO: validation
                //TODO: request to back
                userDao.saveUser(User(text))
                goToMainActivity()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}