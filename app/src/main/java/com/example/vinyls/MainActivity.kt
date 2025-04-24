package com.example.vinyls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.vinyls.ui.crearalbum.CrearAlbumScreen
import com.example.vinyls.ui.crearpremio.CrearPremioScreen
import com.example.vinyls.ui.theme.VinylsTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VinylsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //CrearAlbumScreen()
                    CrearPremioScreen()
                }
            }
        }
    }
}