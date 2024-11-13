package com.example.weathermate.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.model.FavoriteCity
import com.example.weathermate.navigation.WeatherScreens
import com.example.weathermate.widgets.WeatherMateAppBar

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

// display Favorite City Screen
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteCityScreen(
    navController: NavController,
    favoriteCityViewModel: FavoriteCityViewModel = hiltViewModel()
    ) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            WeatherMateAppBar(
                title = "Favorite Cities",
                icon = painterResource(id = R.drawable.ic_back_arrow),
                isHomeScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(contentPadding)
                    .background(Color(0xFF4C9EF1))
            ) {
                val list = favoriteCityViewModel.favoriteList.collectAsState().value
                LazyColumn {
                    // Display each favorite city in a row and allow swipe to delete
                    items(items = list, key = { it.city }) { favorite ->

                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    favoriteCityViewModel.deleteFavorite(favorite)
                                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
                                }
                                true
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = {
                                val color = when (dismissState.dismissDirection) {
                                    DismissDirection.EndToStart -> Color.Red
                                    else -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                CityRow(favorite, navController = navController, favoriteCityViewModel)
                            }
                        )
                    }
                }
            }
        }
    }
}

// function to display the favorite city row
@Composable
fun CityRow(
    favorite: FavoriteCity,
    navController: NavController,
    favoriteCityViewModel: FavoriteCityViewModel
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.HomeScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(CornerSize(6.dp)),
        color = Color(0xFF957DCD) // Secondary color
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp), color = Color.White)

            Text(
                text = favorite.country,
                modifier = Modifier.padding(4.dp),
                color = Color.White,
                style = MaterialTheme.typography.caption
            )
        }
    }
}
