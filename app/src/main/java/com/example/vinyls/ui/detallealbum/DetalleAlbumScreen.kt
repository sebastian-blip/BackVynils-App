package com.example.vinyls.ui.detallealbum

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vinyls.R

@Composable
fun DetalleAlbumScreen(navController: NavController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            HomeHeader2()
            Spacer(modifier = Modifier.height(3.dp))
        }

}

@Composable
fun HomeHeader2( ) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_vinyls),
            contentDescription = "Logo de Vinyls",
            modifier = Modifier
                .height(90.dp)
                .padding(10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "Imágen menú",
            modifier = Modifier
                .height(80.dp)
                .padding(10.dp)
        )
    }
}
