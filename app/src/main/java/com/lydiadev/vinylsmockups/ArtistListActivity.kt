package com.lydiadev.vinylsmockups

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class ArtistListActivity : AppCompatActivity() {

    private lateinit var brunoNavigate: ShapeableImageView
    private lateinit var michaelNavigate: ShapeableImageView
    private lateinit var dualipaNavigate: ShapeableImageView
    private lateinit var galdiveNavigate: ShapeableImageView
    private lateinit var rosaliaNavigate: ShapeableImageView
    private lateinit var sabrinaNavigate: ShapeableImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artists_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
    }
    private fun initListeners(){
        brunoNavigate.setOnClickListener {
            navigateToBruno()
        }
        michaelNavigate.setOnClickListener {
            navigateToMichael()
        }
        dualipaNavigate.setOnClickListener {
            navigateToDualipa()
        }
        rosaliaNavigate.setOnClickListener {
            navigateToRosalia()
        }
        galdiveNavigate.setOnClickListener {
            navigateToGaldive()
        }
        sabrinaNavigate.setOnClickListener {
            navigateToSabrina()
        }

    }

    private fun initComponents(){
        brunoNavigate = findViewById(R.id.brunoNavigate)
        michaelNavigate = findViewById(R.id.michaelNavigate)
        dualipaNavigate = findViewById(R.id.dualipaNavigate)
        rosaliaNavigate = findViewById(R.id.rosaliaNavigate)
        galdiveNavigate = findViewById(R.id.galdiveNavigate)
        sabrinaNavigate = findViewById(R.id.sabrinaNavigate)
    }


    private fun navigateToBruno(){
        val intent = Intent(this, ArtistActivityBruno::class.java)
        startActivity(intent)
    }

    private fun navigateToMichael(){
        val intent = Intent(this, ArtistActivityMichael::class.java)
        startActivity(intent)
    }

    private fun navigateToDualipa(){
        val intent = Intent(this, ArtistActivityDualipa::class.java)
        startActivity(intent)
    }

    private fun navigateToRosalia(){
        val intent = Intent(this, ArtistActivityRosalia::class.java)
        startActivity(intent)
    }

    private fun navigateToGaldive(){
        val intent = Intent(this, ArtistActivityGaldive::class.java)
        startActivity(intent)
    }

    private fun navigateToSabrina(){
        val intent = Intent(this, ArtistActivitySabrina::class.java)
        startActivity(intent)
    }

}