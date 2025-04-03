package com.example.aadityaaryalfinalmobile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment

import androidx.compose.ui.unit.dp
import com.example.aadityaaryalfinalmobile.ScoreManager
import com.example.aadityaaryalfinalmobile.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush


//composable that displays top three score of the session
@Composable
fun HighScoresScreen(onNavigate: (String) -> Unit) {
    //getting top three scores from score Manger
    val scores = ScoreManager.getTopScores()
    val showBackButton = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB388FF), Color(0xFF7C4DFF))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("High Scores", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            //showing either list or empty message
            if (scores.isEmpty()) {
                Text("No scores recorded yet.")
            } else {
                scores.forEachIndexed { index, score ->
                    Text("${index + 1}. ${score.name}: ${score.score} pts")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Button(onClick = { onNavigate(Screen.Welcome.route) }) {
                Text("Go back to Welcome")
            }
        }
    }
}

