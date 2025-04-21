package com.example.vinyls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.vinyls.ui.screens.CrearAlbumScreen
import com.example.vinyls.ui.theme.VinylsTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VinylsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    CrearAlbumScreen()
                }
            }
        }
    }


}
