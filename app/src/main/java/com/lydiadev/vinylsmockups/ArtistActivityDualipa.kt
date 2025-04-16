package com.lydiadev.vinylsmockups

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ArtistActivityDualipa : AppCompatActivity() {

    private lateinit var goback: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artist_dualipa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
    }

    private fun initListeners() {
        goback.setOnClickListener {
            navigateBack()
        }
    }

    private fun initComponents() {
        goback = findViewById<LinearLayout>(R.id.goback)
    }

    private fun navigateBack() {
        val intent = Intent(this, ArtistListActivity::class.java)
        startActivity(intent)
    }

}


