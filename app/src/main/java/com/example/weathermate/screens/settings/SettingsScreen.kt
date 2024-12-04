package com.example.weathermate.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.model.Unit
import com.example.weathermate.widgets.WeatherMateAppBar
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),

) {
    val currentUnit by settingsViewModel.unitList.collectAsState(initial = emptyList())
    val defaultUnit = currentUnit.firstOrNull()?.unit ?: "Metric (C)"

    var unitToggleState by remember { mutableStateOf(defaultUnit == "Imperial (F)") }
    var selectedUnit by remember { mutableStateOf(defaultUnit) }

    Scaffold(
        topBar = {
            WeatherMateAppBar(
                title = "Settings",
                icon = painterResource(id = R.drawable.ic_back_arrow),
                isHomeScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF4C9EF1))
                .padding(paddingValues)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Change Units of Measurement",
                    color = Color.White,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                IconToggleButton(
                    checked = unitToggleState,
                    onCheckedChange = {
                        unitToggleState = it
                        selectedUnit = if (it) "Imperial (F)" else "Metric (C)"
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF2196F3))
                        .padding(8.dp)
                ) {
                    Text(
                        text = if (unitToggleState) "Fahrenheit (F)" else "Celsius (C)",
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        // Update the selected unit in the database
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Unit(unit = selectedUnit))

                        // Refresh the favorite cities with the new unit
                        //favoriteCityViewModel.refreshFavoriteCities(selectedUnit.split(" ")[0].lowercase())
                    },

                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2196F3))
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}
