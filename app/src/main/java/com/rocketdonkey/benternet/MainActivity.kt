package com.rocketdonkey.benternet

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.rocketdonkey.benternet.databinding.ActivityMainBinding

/**
 * Benternet's primary container.
 *
 * All fragments are encapsulated by this container, which itself provides the constant bits
 * of the UI (title bar, navigation, etc.).
 */
class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Navigation.
        setupNavigation()
    }

    /**
     * Sets up the bottom navigation menu and wires up the actions.
     *
     * Navigation is not hierarchical, so there are no directions between fragments.
     */
    private fun setupNavigation() {
        val navController =
            Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_btn_home -> {
                    navController.navigate(R.id.fragment_home)
                    true
                }
                R.id.nav_btn_fireplace -> {
                    navController.navigate(R.id.fragment_fireplace)
                    true
                }
                R.id.nav_btn_tv -> {
                    navController.navigate(R.id.fragment_tv)
                    true
                }
                R.id.nav_btn_lights -> {
                    navController.navigate(R.id.fragment_lights)
                    true
                }
                else -> {
                    Log.w(TAG, "Unknown navigation item: ${item}")
                    false
                }
            }
        }
    }
}