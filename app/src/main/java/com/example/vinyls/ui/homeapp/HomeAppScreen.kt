package com.example.vinyls.ui.homeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
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


@Composable

fun HomeAppScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    )
    {
        HomeHeader()
        SearchBar()
        Spacer(modifier = Modifier.height(24.dp))
        ArtistCarousel()
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
                .padding(16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "Imágen menú",
            modifier = Modifier
                .height(120.dp)
                .padding(16.dp)
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
            .padding(16.dp)
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
    ArtistList()
}

@Composable
fun SectionTitle(title: String, showAddIcon: Boolean = false, onArrowClick: (() -> Unit)? = null, onAddClick: (() -> Unit)? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(title, color = Color.White, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
        if (showAddIcon) {
            IconButton(onClick = { onAddClick?.invoke() }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.Red)
            }
        } else if (onArrowClick != null) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Ver más",
                tint = Color.Red,
                modifier = Modifier
                    .background(Color.White, shape = CircleShape)
                    .padding(8.dp)
                    .clickable { onArrowClick.invoke() }
            )
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
                        .width(74.dp)
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
            .size(74.dp)
            .background(Color.Gray, shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(74.dp)
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