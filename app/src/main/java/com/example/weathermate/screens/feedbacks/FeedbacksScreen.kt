package com.example.weathermate.screens.feedbacks

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FeedbacksScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "We value your feedback!",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = {
            // Create an explicit Intent to open Gmail (if installed)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://mail.google.com/mail/?view=cm&fs=1&to=shamtila5@gmail.com&su=WeatherMate%20Feedback&body=Hi%20team,%0A%0AHere%20is%20my%20feedback:%0A%0A")
            }

            // Check if Gmail is available
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // If Gmail is not installed, show a message
                Toast.makeText(context, "Gmail app not installed", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Send Feedback")
        }
    }
}
