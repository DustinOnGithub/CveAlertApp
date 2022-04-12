package com.example.cvealert

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

//import com.example.cvealert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var drawerLayout: DrawerLayout
    private val timelineFragment: TimelineFragment = TimelineFragment()
    private val subscriptionsFragment: SubscriptionsFragment = SubscriptionsFragment()
    private val settingsFragment: SettingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        val bottomNavigationMenuView = findViewById<BottomNavigationView>(R.id.myNavHostFragment)
        bottomNavigationMenuView.setupWithNavController(navController)

    }
}