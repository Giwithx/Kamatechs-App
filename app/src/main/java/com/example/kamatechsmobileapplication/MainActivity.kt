package com.example.kamatechsmobileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kamatechsmobileapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

//    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

//        drawerLayout = binding.drawerLayout

//        val navController = this.findNavController(R.id.myNavHostFragment)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

//        NavigationUI.setupWithNavController(binding.navView, navController)

        val actionbar = supportActionBar
        actionbar!!.title = "Kamatechs"

    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }
}