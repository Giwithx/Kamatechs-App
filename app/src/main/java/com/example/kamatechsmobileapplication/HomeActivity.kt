package com.example.kamatechsmobileapplication


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.kamatechsmobileapplication.databinding.ActivityHomeBinding
import com.example.kamatechsmobileapplication.fragments.AboutFragment
import com.example.kamatechsmobileapplication.fragments.FAQFragment
import com.example.kamatechsmobileapplication.login.LoginFragment
import com.example.kamatechsmobileapplication.register.RegisterFragment
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )
        supportActionBar?.title = "Home"

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView : NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeActivity -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.aboutFragment -> {
                    replaceFragment(AboutFragment())
                    true
                }
                R.id.FAQFragment -> {
                    replaceFragment(FAQFragment())
                    true
                }
                R.id.weatherActivity -> {
                    startActivity(Intent(this, WeatherActivity::class.java))
                    true
                }
                R.id.storageActivity -> {
                    startActivity(Intent(this, StorageActivity::class.java))
                    true
                }
                R.id.loginFragment -> {
                    replaceFragment(LoginFragment())
                    true
                }
                R.id.registerFragment -> {
                    replaceFragment(RegisterFragment())
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeActivity -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.aboutFragment -> {
                    replaceFragment(AboutFragment())
                    true
                }
                R.id.FAQFragment -> {
                    replaceFragment(FAQFragment())
                    true
                }
                R.id.weatherActivity -> {
                    startActivity(Intent(this, WeatherActivity::class.java))
                    true
                }
                R.id.storageActivity -> {
                    startActivity(Intent(this, StorageActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.aboutFragment -> {
                replaceFragment(AboutFragment())
            }
        }
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private  fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeLayout,fragment)
        fragmentTransaction.addToBackStack(null).commit()
    }

}