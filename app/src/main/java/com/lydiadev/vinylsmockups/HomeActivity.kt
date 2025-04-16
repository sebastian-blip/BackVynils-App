package com.lydiadev.vinylsmockups

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class HomeActivity : AppCompatActivity() {

    private lateinit var artistNavigate: ImageView
    private lateinit var albumNavigate: ImageView
    private lateinit var brunoNavigate: ShapeableImageView
    private lateinit var michaelNavigate: ShapeableImageView
    private lateinit var dualipaNavigate: ShapeableImageView
    private lateinit var rosaliaNavigate: ShapeableImageView
    private lateinit var abbeyNavigate: ShapeableImageView
    private lateinit var kindNavigate: ShapeableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
    }

    private fun initListeners(){
        albumNavigate.setOnClickListener {
            navigateToAlbums()
        }
        artistNavigate.setOnClickListener {
            navigateToArtists()
        }
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
        abbeyNavigate.setOnClickListener {
            navigateToAbbey()
        }
        kindNavigate.setOnClickListener {
            navigateToAbbey()
        }
    }

    private fun initComponents(){
        albumNavigate = findViewById(R.id.album_navigate)
        artistNavigate = findViewById(R.id.artist_navigate)
        brunoNavigate = findViewById(R.id.brunoNavigate)
        michaelNavigate = findViewById(R.id.michaelNavigate)
        dualipaNavigate = findViewById(R.id.dualipaNavigate)
        rosaliaNavigate = findViewById(R.id.rosaliaNavigate)
        abbeyNavigate = findViewById(R.id.abbeyNavigate)
        kindNavigate = findViewById(R.id.kindNavigate)
    }

    private fun navigateToAlbums(){
        val intent = Intent(this, AlbumListActivity::class.java)
        startActivity(intent)
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

    private fun navigateToArtists(){
        val intent = Intent(this, ArtistListActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToAbbey(){
        val intent = Intent(this, AlbumActivity::class.java)
        startActivity(intent)
    }

}