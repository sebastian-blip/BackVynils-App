package com.lydiadev.vinylsmockups

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlin.jvm.java

abstract class BaseActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupToolbar(title: String, showBackButton: Boolean = false) {
        initComponents()
        setSupportActionBar(toolbar)

        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)

        // Click on the logo takes user to HomeActivity
        toolbar.setNavigationOnClickListener {
            if (showBackButton) {
                onBackPressedDispatcher.onBackPressed()
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    fun initComponents(){
        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        navigationView =findViewById(R.id.navigationView)
    }

}