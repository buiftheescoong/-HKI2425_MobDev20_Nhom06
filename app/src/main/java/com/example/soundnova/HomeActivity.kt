package com.example.soundnova

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.findNavController
import com.example.soundnova.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.musicPlayerFragment, R.id.lyricsFragment, R.id.karaokeFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.libraryFragment -> {
                    navController.navigate(R.id.libraryFragment)
                    true
                }

                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }

                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
                R.id.libraryFragment -> binding.bottomNavigationView.menu.findItem(R.id.libraryFragment).isChecked = true
                R.id.searchFragment -> binding.bottomNavigationView.menu.findItem(R.id.searchFragment).isChecked = true
                R.id.settingsFragment -> binding.bottomNavigationView.menu.findItem(R.id.settingsFragment).isChecked = true
            }
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)

        val currentEntry = navController.currentBackStackEntry

        if (currentEntry?.destination?.id == R.id.homeFragment && navController.previousBackStackEntry == null) {
            super.onBackPressed()
        } else {
            navController.popBackStack()
        }
    }
}
