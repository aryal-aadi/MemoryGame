package com.example.aadityaaryalfinalmobile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.aadityaaryalfinalmobile.GameData
import com.example.aadityaaryalfinalmobile.Screen

//composable for the welcome screen of the game
@Composable
fun WelcomeScreen(onNavigate: (String) -> Unit) {

    //storing and syncing username across app
    var playerName by remember { mutableStateOf(GameData.playerName) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB388FF), Color(0xFF7C4DFF)),

                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to the Memory Game!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            //instruction card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4A148C)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "How to Play:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text( //game instructions
                        text = """
                            4 tiles light up for 3 seconds.
                            Memorize and select them in 5 seconds.
                            After 3 rounds -> 5 tiles.
                            A wrong pick or timeout ends the game.
                            Score based on accuracy & rounds.
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            //name input textfield
            OutlinedTextField(
                value = playerName,
                onValueChange = {
                    playerName = it
                    GameData.playerName = it
                },
                label = { Text("Your Name Buddy", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )


            Spacer(modifier = Modifier.height(16.dp))

            //button that navigates to the game page
            Button(
                onClick = { onNavigate(Screen.Game.route) },
                enabled = playerName.isNotBlank(),
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.linearGradient(listOf(Color.White, Color.White))
                )
            ) {
                Text("Start Game")
            }

        }
    }
}
