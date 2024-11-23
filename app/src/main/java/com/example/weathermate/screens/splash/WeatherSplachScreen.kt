package com.example.weathermate.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val defaultCity = "Helsinki"
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                })
        )
        delay(2000L)
        navController.navigate(WeatherScreens.HomeScreen.name + "/$defaultCity")
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF673AB7))
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherMateLogo(scale)
        }
    }
}

@Composable
fun WeatherMateLogo(scale: Animatable<Float, AnimationVector1D>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.scale(scale.value)
    ) {
        // Box to overlay two images
        Box(
            modifier = Modifier
                .width(300.dp),




            contentAlignment = Alignment.Center
        ) {
            // First image
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "First Image",
                modifier = Modifier


            )

            // Second image
            Image(
                painter = painterResource(id = R.drawable.ic_logo1),
                contentDescription = "Second Image",
                modifier = Modifier.size(100.dp).padding(top = 16.dp)

            )
        }
        // App name
        Text(
            text = "WeatherMate App",
            style = MaterialTheme.typography.h4,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}