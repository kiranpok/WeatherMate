package com.example.weathermate.screens.feedbacks

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.ui.platform.LocalContext
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
                .padding(top = innerPadding.calculateTopPadding()), // Align directly below AppBar
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Align content to the top
        ) {
            // Ensure no additional space above the text
            Text(
                text = "Thank you for helping us improve our services!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp) // Only bottom padding
            )

            // Description text
            Text(
                text = "Use the Send feedback button to give us feedback on the application. The button will take you to your mobile phone’s email program.",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "We will process all feedback we receive but cannot guarantee a personal reply.",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Feedback button
            OutlinedButton(
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:APP_EMAIL_ADDRESS")
                        putExtra(Intent.EXTRA_SUBJECT, "WeatherMate Feedback")
                        putExtra(Intent.EXTRA_TEXT, "Hi team,\n\nHere is my feedback:\n\n")
                    }

                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(emailIntent)
                    } else {
                        val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("APP_EMAIL_ADDRESS"))
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
                border = BorderStroke(1.dp, Color(0xFF2196F3)),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2196F3))
            ) {
                // Button content
                // Button content
                Text(
                    text = "Send feedback",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.arrow_outward), // Ensure this exists in drawable
                    contentDescription = "Send feedback icon",
                    modifier = Modifier.size(24.dp) // Increase size for visibility
                )
            }
        }
    }
}
