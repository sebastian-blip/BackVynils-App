package com.example.vinyls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinyls.ui.crearalbum.CrearAlbumScreen
import com.example.vinyls.ui.crearpremio.CrearPremioScreen
import com.example.vinyls.ui.homeapp.HomeAppScreen
import com.example.vinyls.ui.login.LoginScreen
import com.example.vinyls.ui.theme.VinylsTheme
import com.example.vinyls.ui.detalleartista.DetalleArtistaScreen
import com.example.vinyls.ui.albumlist.AlbumListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinylsTheme {
                val navController: NavHostController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onContinueClick = {
                                    navController.navigate("home")
                                }
                            )
                        }
                        composable("home") {
                            HomeAppScreen(navController)
                        }
                        composable("crear_album") {
                            CrearAlbumScreen(navController)
                        }
                        composable("crear_premios") {
                            CrearPremioScreen(navController)
                        }
                        composable("detalle_artista/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 100
                            DetalleArtistaScreen(navController = navController, artistaId = id)
                        }
                        composable("listar_albumns") {
                            AlbumListScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}