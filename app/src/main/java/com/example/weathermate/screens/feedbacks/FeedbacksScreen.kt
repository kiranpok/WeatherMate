package com.example.weathermate.screens.feedbacks

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weathermate.R
import com.example.weathermate.widgets.WeatherMateAppBar

@Composable
fun FeedbacksScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            WeatherMateAppBar(
                title = "Feedbacks",
                icon = painterResource(id = R.drawable.ic_back_arrow),
                isHomeScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()), // Ensure content is below the AppBar
            horizontalAlignment = Alignment.CenterHorizontally, // Center all children horizontally
            verticalArrangement = Arrangement.Top // Align content to the top
        ) {
            // Title text
            Text(
                text = "Thank you for helping us improve our services!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp) // Add spacing below the title
            )

            // Description text 1
            Text(
                text = "Use the Send feedback button to give us feedback on the application. The button will take you to your mobile phone’s email program.",
                fontSize = 14.sp,
                textAlign = TextAlign.Justify, // Center-align the text content
                modifier = Modifier
                    .fillMaxWidth() // Span the full width of the screen
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding
            )

            // Description text 2
            Text(
                text = "We will process all feedback we receive but cannot guarantee a personal reply.",
                fontSize = 14.sp,
                textAlign = TextAlign.Justify, // Center-align the text content
                modifier = Modifier
                    .fillMaxWidth() // Span the full width of the screen
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding
            )
            Spacer(modifier = Modifier.width(18.dp))
            Button(
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:weathermate@gmail.com") // Set recipient
                        putExtra(Intent.EXTRA_SUBJECT, "WeatherMate Feedback") // Set email subject
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Hi team,\n\nHere is my feedback:\n\n"
                        ) // Set email body
                    }

                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(emailIntent)
                    } else {
                        val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822" // Use RFC822 type for email
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("weathermate@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "WeatherMate Feedback")
                            putExtra(Intent.EXTRA_TEXT, "Hi team,\n\nHere is my feedback:\n\n")
                        }

                        if (fallbackIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(
                                Intent.createChooser(
                                    fallbackIntent,
                                    "Choose Email App"
                                )
                            )
                        } else {
                            Toast.makeText(context, "No email app installed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF003366)), // Navy Blue background color
                shape = RoundedCornerShape(50), // Make the button rounded

            ) {
                Text(
                    text = "Send feedback",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White // White color for text
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.arrow_outward),
                    contentDescription = "Send feedback icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White // White color for icon
                )
            }

        }
    }
}

