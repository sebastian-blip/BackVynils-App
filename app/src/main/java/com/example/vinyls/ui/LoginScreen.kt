package com.example.vinyls.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vinyls.R
import com.example.vinyls.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onContinueClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_login),
            contentDescription = "Logo Vinilos",
            modifier = Modifier
                .width(366.dp)
                .height(196.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.login_icon),
            contentDescription = "Login Icon",
            modifier = Modifier
                .width(188.dp)
                .height(230.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(35.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                loginViewModel.login() // ✅ Usamos la función correcta
                onContinueClick()
            },
            modifier = Modifier
                .width(300.dp)
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFED1C24)
            )
        ) {
            Text(text = stringResource(id = R.string.continue_login))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.terms),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.width(300.dp)
        )
    }
}
