package com.example.weathermate.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weathermate.R
//import com.example.weathermate.model.Favorite
import com.example.weathermate.navigation.WeatherScreens
//import com.example.weathermate.screens.favorite.FavoriteViewModel

// Custom AppBar for the application
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WeatherMateAppBar(
    title: String = "Title",
    icon: Painter? = null,
    isHomeScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialogue = showDialog, navController = navController)
    }
    val expanded = remember { mutableStateOf(false) }



    TopAppBar(
        title = {
            if (isHomeScreen)
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "location icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            Text(
                text = title,
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
        },
        actions = {
            if (isHomeScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = {
                    showDialog.value = !showDialog.value
                    expanded.value = !expanded.value
                    onButtonClicked.invoke()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "menu icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "back arrow",
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onButtonClicked.invoke()
                        }
                )
            }


        },   backgroundColor = Color(0xFF4C9EF1), // Set the background color to the specified color
        elevation = elevation)
}

// Dropdown menu for the Favorite, Settings, Alerts, and Feedback
@Composable
fun ShowSettingDropDownMenu(showDialogue: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("Favorite", "Settings", "Alerts", "Feedback", )
    Column (modifier = Modifier.fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)){
        DropdownMenu(expanded = expanded,
            onDismissRequest =  { expanded = false },
            modifier = Modifier.width(140.dp)
                .background(Color.White)) {
            items.forEachIndexed {index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialogue.value = false

                }) {
                    Icon(imageVector = when (text) {
                        "Favorite" -> Icons.Default.FavoriteBorder
                        "Settings" -> Icons.Default.Settings
                        "Alerts" -> Icons.Default.Notifications
                        else -> Icons.Default.Info
                    }, contentDescription = null,
                        tint = Color.LightGray,
                    )
                    Text(text = text,
                        fontWeight = FontWeight.W300,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "Favorite" -> WeatherScreens.FavoriteCityScreen.name
                                    "Settings" -> WeatherScreens.SettingsScreen.name
                                    "Alerts" -> WeatherScreens.AlertsScreen.name
                                    else -> WeatherScreens.FeedbacksScreen.name
                                }
                            )

                        }
                    )
                }


            }
        }

    }
}
