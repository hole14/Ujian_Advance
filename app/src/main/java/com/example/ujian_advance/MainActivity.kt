package com.example.ujian_advance

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ujian_advance.data.ViewModelFactory
import com.example.ujian_advance.databinding.ActivityMainBinding
import com.example.ujian_advance.ui.setting.switchmode.SettingPreferences
import com.example.ujian_advance.ui.setting.switchmode.SettingViewModel
import com.example.ujian_advance.ui.setting.switchmode.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SettingPreferences.getInstance(application.dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_upcoming, R.id.navigation_finished, R.id.navigation_favorite, R.id.navigation_setting
            )
        )
        navView.setupWithNavController(navController)
    }
}