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
import androidx.compose.ui.graphics.Color
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
    // Observe the current unit from the ViewModel
    val currentUnit by settingsViewModel.unitList.collectAsState(initial = emptyList())
    val defaultUnit = currentUnit.firstOrNull()?.unit ?: "Metric (C)"

    // Track the toggle state and selected unit
    var unitToggleState by remember { mutableStateOf(defaultUnit == "Imperial (F)") }
    var selectedUnit by remember { mutableStateOf(defaultUnit) }

    // Main scaffold layout for the Settings screen
    Scaffold(
        topBar = {
            // App bar for the Settings screen
            WeatherMateAppBar(
                title = "Settings", // Title of the screen
                icon = painterResource(id = R.drawable.ic_back_arrow), // Back icon
                isHomeScreen = false,
                navController = navController
            ) {
                navController.popBackStack() // Navigate back on clicking the back icon
            }
        }
    ) { paddingValues ->
        // Background container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF4C9EF1)) // Light blue background
                .padding(paddingValues) // Padding from the scaffold
        ) {
            // Main content layout
            Column(
                verticalArrangement = Arrangement.Center, // Center the content vertically
                horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
                modifier = Modifier.fillMaxSize()
            ) {
                // Title text
                Text(
                    text = "Change Units of Measurement",
                    color = Color.White, // Changed to blue color
                    fontSize = 22.sp, // Font size
                    modifier = Modifier.padding(bottom = 20.dp) // Spacing below the title
                )

                // Styled IconToggleButton
                Button(
                    onClick = {
                        unitToggleState = !unitToggleState // Toggle the state
                        selectedUnit = if (unitToggleState) "Imperial (°F)" else "Metric (°C)" // Update the selected unit
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), // White background for contrast
                    shape = RoundedCornerShape(12.dp), // Rounded corners
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // Set button width to half the screen width
                        .padding(8.dp) // Padding around the button
                ) {
                    // Display the current unit
                    Text(
                        text = if (unitToggleState) "Fahrenheit (°F)" else "Celsius (°C)", // Toggle text
                        color = Color(0xFF2196F3), // Changed to blue color for text
                        fontSize = 16.sp // Font size
                    )
                }

                // Styled Save button
                Button(
                    onClick = {
                        // Update the selected unit in the database
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Unit(unit = selectedUnit))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), // White background for contrast
                    shape = RoundedCornerShape(34.dp), // Rounded corners
                    modifier = Modifier
                        .padding(16.dp) // Padding around the button
                        .align(Alignment.CenterHorizontally) // Center the button horizontally
                ) {
                    // Save button text
                    Text(
                        text = "Save",
                        color = Color(0xFF2196F3), // Changed to blue color for text
                        fontSize = 17.sp // Font size
                    )
                }
            }
        }
    }
}
