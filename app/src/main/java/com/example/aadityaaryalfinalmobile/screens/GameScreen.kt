package com.example.aadityaaryalfinalmobile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random
import com.example.aadityaaryalfinalmobile.GameData
import com.example.aadityaaryalfinalmobile.GameController
import androidx.compose.ui.graphics.Brush


//composable for the main Game Screen
@Composable
fun GameScreen(onNavigate: (String) -> Unit) {
    val controller = remember { GameController() }
    val gridSize = 6
    val totalTiles = gridSize * gridSize

    var highlightedTiles by remember { mutableStateOf(listOf<Int>()) }
    val selectedTiles = remember { mutableStateListOf<Int>() }

    var showHighlights by remember { mutableStateOf(true) }
    var isInputEnabled by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf("") }

    //triggering new rounds
    LaunchedEffect(controller.round) {
        selectedTiles.clear()
        showMessage = ""
        isInputEnabled = false
        showHighlights = true

        highlightedTiles = controller.getTilesToHighlight()

        delay(3000) // highlighting tiles
        showHighlights = false
        isInputEnabled = true

        delay(5000) //5 seconds for user input

        if (!controller.gameOver) {
            isInputEnabled = false
            val isCorrect = controller.checkAnswer(highlightedTiles, selectedTiles)

            if (isCorrect) {
                val earned = controller.updateScore()
                showMessage = "Correct! +$earned pts"
            } else {
                controller.endGame(GameData.playerName)
                showMessage = "Game Over! Final Score: ${controller.score}"
            }
        }
    }

    //ui for game screen
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Round ${controller.round}", style = MaterialTheme.typography.headlineSmall)
            Text("Score: ${controller.score}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(gridSize),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(totalTiles) { index ->
                    val isHighlighted = highlightedTiles.contains(index)
                    val isSelected = selectedTiles.contains(index)

                    val tileColor = when {
                        showHighlights && isHighlighted -> Color.Yellow
                        !showHighlights && isSelected -> Color.Green
                        else -> Color.Gray
                    }

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(color = tileColor, shape = MaterialTheme.shapes.medium)
                            .clickable(enabled = isInputEnabled) {
                                if (isSelected) {
                                    selectedTiles.remove(index)
                                } else {
                                    selectedTiles.add(index)
                                }
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showMessage.isNotBlank()) {
                Text(showMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (controller.gameOver) {
                Button(onClick = { onNavigate("highscores") }) {
                    Text("Go to High Scores")
                }
            }
        }
    }
}


////fucntion that compares highlighted tiles and selected tile
//fun checkResult(
//    highlighted: List<Int>,
//    selected: List<Int>,
//    onSuccess: () -> Unit,
//    onFailure: () -> Unit
//) {
//    if (selected.sorted() == highlighted.sorted()) {
//        onSuccess()
//    } else {
//        onFailure()
//    }
//}
