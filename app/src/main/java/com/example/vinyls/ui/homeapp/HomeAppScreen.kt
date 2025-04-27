package com.example.vinyls.ui.homeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vinyls.R
import androidx.navigation.NavController




@Composable
fun HomeAppScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeHeader()
        Spacer(modifier = Modifier.height(3.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(2.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ArtistCarousel()
            Spacer(modifier = Modifier.height(3.dp))
            AlbumCarousel(navController)
            Spacer(modifier = Modifier.height(3.dp))
            AwardsCarousel(navController)
        }
    }
}


@Composable
fun HomeHeader( ) {
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


@Composable

fun SearchBar(){

    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Buscar artista", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        ),
        singleLine = true,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable

fun ArtistCarousel(){
    SectionTitle(title = "Artistas")
    Spacer(modifier = Modifier.height(6.dp))
    ArtistList()
}

@Composable

fun AlbumCarousel(navController: NavController){
    SectionTitle(
        title = "Albumes",
        onAddClick = {
            navController.navigate("crear_album")
        }
    )
    Spacer(modifier = Modifier.height(6.dp))
    AlbumList()
}

@Composable

fun AwardsCarousel(navController: NavController){
    SectionTitle(
        title = "Premios",
        onAddClick = {
            navController.navigate("crear_premios")
        }
    )
    Spacer(modifier = Modifier.height(6.dp))
    AwardsList()
}

@Composable
fun SectionTitle(
    title: String,
    showAddIcon: Boolean = true,
    onAddClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        if (showAddIcon) {
            IconButton(
                onClick = { onAddClick?.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}


@Composable
fun ArtistList() {
    val avatars = listOf(
        Avatar("Bruno Mars", R.drawable.avatar_1),
        Avatar("Michael Jackson", R.drawable.avatar_2),
        Avatar("Dua Lipa", R.drawable.avatar_3),
        Avatar("Rosalía", R.drawable.avatar_4)
    )
    LazyRow {
        items(avatars) { artist ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                CircleAvatar(imageResId = artist.imageResId)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = artist.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .width(78.dp)
                        .padding(horizontal = 4.dp),
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CircleAvatar(imageResId: Int) {
    Box(
        modifier = Modifier
            .size(78.dp)
            .background(Color.Gray, shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(78.dp)
                .clip(CircleShape)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop

        )
    }
}

data class Avatar(
    val name: String,
    val imageResId: Int
)

data class AvatarAlbum(
    val name: String,
    val imageResId: Int,
    val album_artist: String
)

@Composable
fun AlbumList() {
    val avatarsAlbum = listOf(
        AvatarAlbum("Abbey Road", R.drawable.avatar_album_1, "The Beatles"),
        AvatarAlbum("Kind of Blue", R.drawable.avatar_album_2, "Miles Davis")
    )
    LazyRow {
        items(avatarsAlbum) { album ->
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(end = 36.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Gray)
                ){
                    Image(
                        painter = painterResource(id = album.imageResId),
                        contentDescription = "Avatar_album",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop

                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = album.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    textAlign = TextAlign.Left
                )
                Text(
                    text = album.album_artist,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    textAlign = TextAlign.Left // Alineamos a la izquierda
                )
            }
        }
    }
}

@Composable
fun AwardsList() {
    val avatarsAwards = listOf(
        Avatar("Mejor colaboración", R.drawable.avatar_premios_1),
        Avatar("Artista más escuchado", R.drawable.avatar_premios_2),
        Avatar("Artista Revelación", R.drawable.avatar_premios_3),
        Avatar("Artista del año", R.drawable.avatar_premios_4)
    )
    LazyRow {
        items(avatarsAwards) { award ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                CircleAvatar(imageResId = award.imageResId)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = award.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .width(78.dp)
                        .padding(horizontal = 4.dp),
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

