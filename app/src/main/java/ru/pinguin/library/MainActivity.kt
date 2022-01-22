package ru.pinguin.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.pinguin.library.database.UsersDatabase
import ru.pinguin.library.database.UsersRepository
import ru.pinguin.library.databinding.ActivityMainBinding
import ru.pinguin.library.ui.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    lateinit var mainFragment: MainFragment

    private lateinit var usersRepository: UsersRepository

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root

        val userDao = UsersDatabase.getDatabase(this).userDao()
        usersRepository = UsersRepository(userDao)

        initToolbar()
        initDrawer()

        setContentView(view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initToolbar() {
        toolbar = viewBinding.mainToolbar
        drawer = viewBinding.drawerLayout
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
    }

    private fun initDrawer() {
        val navigation = viewBinding.navigationView
        navigation.setNavigationItemSelectedListener { item: MenuItem ->
            try {
                when (item.itemId) {
//                    R.id.drawer_about -> navController.navigate(R.id.action_mainFragment_to_aboutFragment)
                    R.id.drawer_exit -> exit()
                }
            } catch (e: IllegalArgumentException) {

            }
            drawer.closeDrawer(GravityCompat.START)
            Log.i(TAG, "initToolbar: ${item.itemId}")
            true
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val textView = navigation.getHeaderView(0).findViewById<TextView>(R.id.drawer_name)
        textView.text = usersRepository.getUser()
    }

    fun getToolbar(): Toolbar = toolbar

    override fun onBackPressed() {
//        if (mainFragment.onBackPressed())
        super.onBackPressed()
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    private fun exit() {
        usersRepository.deleteUser()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}